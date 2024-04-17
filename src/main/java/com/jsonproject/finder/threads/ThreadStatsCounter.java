package com.jsonproject.finder.threads;

import com.jsonproject.finder.json.StatisticJsonAdder;
import com.jsonproject.finder.statistic.Statistic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadStatsCounter {
    private static final Logger logger = LogManager.getLogger(ThreadStatsCounter.class);
    private final List<File> files;
    private final int numberOfThreads;


    public ThreadStatsCounter(List<File> files, int numberOfThreads) {
        this.files = files;
        this.numberOfThreads = numberOfThreads;
    }

    public void makeStatistic(Statistic statistic, String fieldName) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        Queue<File> fileQueue = new ConcurrentLinkedQueue<>(files);
        CountDownLatch latch = new CountDownLatch(files.size());

        JsonProcessingStats jsonParcingStats = new JsonProcessingStats();



        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                StatisticJsonAdder adder = new StatisticJsonAdder(statistic, fieldName);
                File currentFile;
                while ((currentFile = fileQueue.poll()) != null) {
                    jsonParcingStats.addAllFilesProcessed();
                    try {
                        try {
                            adder.addFieldValuesIntoStats(currentFile);
                        } catch (IOException e) {
                            logger.warn(String.format("File %s is corrupted, aborting it", currentFile.getName()), e);
                            jsonParcingStats.addInvalidFiles();
                        }
                        jsonParcingStats.addTotalObjectsProcessed(adder.getTotalObjectLastFileProcessed());
                        jsonParcingStats.addObjectsWithMistake(adder.getFailedObjectLastFileProcessed());
                        latch.countDown();
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        try {
            latch.await();
            logger.info(jsonParcingStats.getStatisticString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdownNow();
    }




}
