package com.jsonproject.finder;

import com.jsonproject.finder.entity.TaxiDriver;
import com.jsonproject.finder.statistic.Statistic;
import com.jsonproject.finder.statistic.StatisticChooser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.MapsCompare;
import utils.TaxiDriverUtil;
import utils.jsoncreation.JsonFileCreatingBlanks;
import utils.jsoncreation.TaxiDriverCreator;
import utils.xmlparsing.XmlStatsParser;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class JsonFinderTest {

    private static final List<String> fields = TaxiDriverUtil.getTaxiDriverFields();


    @ParameterizedTest
    @MethodSource("getFields")
    void validStatisticWithCorrectData(String parameter, @TempDir Path tempDir){
        JsonFinder.setXmlStatsPath(tempDir.toString());
        String filename = "drivers.json";
        Path filePath = tempDir.resolve(filename);

        Random random = new Random(13);
        List<TaxiDriver> drivers = getDriversList(random, 40);

        JsonFileCreatingBlanks.createFile(filePath.toString(), drivers);

        JsonFinder.main(new String[]{tempDir.toString(), parameter});

        String xmlStatsPath = String.format("%s/%s", tempDir, JsonFinder.getStatisticFileName());

        Map<String, Integer> xmlStats = getXmlStatistic(xmlStatsPath);
        Map<String, Integer> realStats = getRealStatistic(drivers, parameter);

        assertTrue(MapsCompare.compare(xmlStats, realStats));
    }



    private static List<String> getFields(){
        return fields;
    }

    private Map<String, Integer> getXmlStatistic(String path) {
       return XmlStatsParser.parseXmlToMap(path);
    }

    private Map<String, Integer> getRealStatistic(List<TaxiDriver> drivers, String parameter){
        Statistic realStats = StatisticChooser.getStatistic(parameter);
        drivers.forEach(driver -> {
            try {
                realStats.addValue(TaxiDriverUtil.getFieldValue(driver, parameter));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException("Failed to access field: " + parameter, e);
            }
        });
        return realStats.getStatistic();
    }


    private List<TaxiDriver> getDriversList(Random random, int amount){
        List<TaxiDriver> drivers = new ArrayList<>();
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(random);
        for (int i=0; i <amount; i++){
            drivers.add(taxiCreator.getTaxiDriver());
        }
        return drivers;
    }


}
