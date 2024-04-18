package com.jsonproject.finder;

import com.jsonproject.finder.entity.TaxiDriver;
import com.jsonproject.finder.json.StatisticJsonAdder;
import com.jsonproject.finder.statistic.Statistic;
import com.jsonproject.finder.statistic.StatisticChooser;
import com.jsonproject.finder.threads.ThreadStatsCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.MapsCompare;
import utils.TaxiDriverUtil;
import utils.jsoncreation.JsonFileCreatingBlanks;
import utils.jsoncreation.TaxiDriverCreator;
import utils.xmlparsing.XmlStatsParser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadTest {
    private static final Logger logger = LogManager.getLogger(JsonFinder.class);
    private static final List<String> fields = TaxiDriverUtil.getTaxiDriverFields();
    private static final int OBJECTS_IN_ONE_FILE = 400;
    private static final int FILES_AMOUNT = 40;
    private static final int THREAD_AMOUNT_FOR_CREATING_FILES = 10;

    @Disabled
    @ParameterizedTest
    @MethodSource("getThreadNumber")
    void validStatisticWithCoupleThreadsAndCorrectData(Integer numberOfThreads, @TempDir Path tempDir) {
        System.out.printf("========Test with %d threads==========\n", numberOfThreads);
        String parameter = fields.get(0);
        JsonFinder.setXmlStatsPath(tempDir.toString());
        JsonFinder.setThreadAmount(numberOfThreads);

        Random random = new Random(13);
        Statistic realStatistic = StatisticChooser.getStatistic(parameter);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_AMOUNT_FOR_CREATING_FILES);

        List<Callable<Void>> tasks = getTasks(tempDir, random, parameter, realStatistic);
        try {
            List<Future<Void>> futures = executor.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error occurred during JSON file creating: ", e);
        }
        executor.shutdown();


        long startTime = System.currentTimeMillis();
        JsonFinder.main(new String[]{tempDir.toString(), parameter});
        long stopTime = System.currentTimeMillis();

        logger.info(String.format("Test was successfully made by %d ms", stopTime - startTime  ));

        String xmlStatsPath = String.format("%s/%s", tempDir, JsonFinder.getStatisticFileName());

        Map<String, Integer> xmlStats = getXmlStatistic(xmlStatsPath);

        Map<String, Integer> realStats = getXmlStatistic(xmlStatsPath);

        if(numberOfThreads ==8){
            System.out.println();
        }
        assertTrue(MapsCompare.compare(xmlStats, realStats));
    }


    private static List<Integer> getThreadNumber() {
        return List.of(1, 2, 4, 8);
    }




    private Map<String, Integer> getXmlStatistic(String path) {
        return XmlStatsParser.parseXmlToMap(path);
    }

    private void addDriversToRealStatistic(Statistic realStats, List<TaxiDriver> drivers, String parameter) {
        drivers.forEach(driver -> {
            try {
                realStats.addValue(TaxiDriverUtil.getFieldValue(driver, parameter));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException("Failed to access field: " + parameter, e);
            }
        });
    }


    private List<TaxiDriver> getDriversList(Random random, int amount) {
        List<TaxiDriver> drivers = new ArrayList<>();
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(random);
        for (int i = 0; i < amount; i++) {
            drivers.add(taxiCreator.getTaxiDriver());
        }
        return drivers;
    }


    private List<Callable<Void>> getTasks(Path tempDir, Random random, String parameter, Statistic realStats) {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < FILES_AMOUNT; i++) {
            int finalI = i;
            tasks.add(() -> {
                Path filePath = tempDir.resolve(String.format("drivers_%d.json", finalI));
                List<TaxiDriver> drivers = getDriversList(random, OBJECTS_IN_ONE_FILE);
                JsonFileCreatingBlanks.createFile(filePath.toString(), drivers);
                addDriversToRealStatistic(realStats, drivers, parameter);

                return null;
            });
        }
        return tasks;
    }



}
