package com.jsonproject.finder.threads;

import com.jsonproject.finder.json.StatisticJsonAdder;
import com.jsonproject.finder.statistic.Statistic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class ThreadStatsCounter {
    private static final Logger logger = LogManager.getLogger(ThreadStatsCounter.class);
    private final List<File> files;
    private final int numberOfThreads;
    private JsonFileStats jsonParsingStats;


    public ThreadStatsCounter(List<File> files, int numberOfThreads) {
        this.files = files;
        this.numberOfThreads = numberOfThreads;
    }

    public void makeStatistic(Statistic statistic, String fieldName) {
        if (files.size() ==0){
            logger.warn("There is no single file for parsing. Aborting");
            return;
        }


        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        jsonParsingStats = new JsonFileStats();

        List<Callable<Void>> tasks = getTasks(statistic, fieldName);

        try {
            List<Future<Void>> futures = executor.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted while json parsing:", e);
        } catch (Exception e) {
            logger.error("Error while executor works", e);
        } finally {
            executor.shutdown();
            logger.info(jsonParsingStats.getStatisticString());
        }

    }

    private List<Callable<Void>> getTasks(Statistic statistic, String fieldName) {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (File file : files) {
            tasks.add(() -> {
                StatisticJsonAdder adder = new StatisticJsonAdder(statistic, fieldName);
                jsonParsingStats.addAllFilesProcessed();

                try {
                    adder.addFieldValuesIntoStats(file);
                } catch (Exception e) {
                    logger.warn(String.format("Exception in stats making with file %s: %s", file.getName(), e.getMessage()), e);
                    jsonParsingStats.addInvalidFiles();
                }
                jsonParsingStats.addTotalObjectsProcessed(adder.getTotalObjectLastFileProcessed());
                jsonParsingStats.addObjectsWithMistake(adder.getFailedObjectLastFileProcessed());

                return null;
            });
        }
        return tasks;
    }


}
