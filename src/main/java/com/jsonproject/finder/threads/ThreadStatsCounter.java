package com.jsonproject.finder.threads;

import com.jsonproject.finder.json.StatisticJsonAdder;
import com.jsonproject.finder.statistic.Statistic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The ThreadStatsCounter class is designed to manage the concurrent processing of a large number of JSON files,
 * aggregating statistics about some parameters through multiple threads. This class facilitates efficient parsing
 * and statistical analysis of the parsing JSON files using a specified number of worker threads. All parsing statistics
 * then will be logged.
 *
 * @see JsonFileStats
 */
public class ThreadStatsCounter {
    private static final Logger logger = LogManager.getLogger(ThreadStatsCounter.class);
    private final List<File> files;
    private final int numberOfThreads;
    private JsonFileStats jsonParsingStats;

    /**
     * Constructs a new ThreadStatsCounter with a list of files and a specific number of threads.
     *
     * @param files the list of files to be processed.
     * @param numberOfThreads the number of threads to use for processing the files.
     */
    public ThreadStatsCounter(List<File> files, int numberOfThreads) {
        this.files = files;
        this.numberOfThreads = numberOfThreads;
    }

    /**
     * Processes the provided files to collect statistics. This method initializes a fixed thread pool,
     * submits parsing tasks for each file, and handles synchronization of thread completion before
     * shutting down the executor. It also logs the comprehensive statistics after all files are processed.
     *
     * @param statistic the statistic object used for recording data from each file.
     * @param fieldName the specific field name to extract data for statistics.
     */
    public void addDataToStatisticFromFiles(Statistic statistic, String fieldName) {
        if (files.size() == 0) {
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
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error occurred during JSON file processing: ", e);
        }
        executor.shutdown();

        logger.info(jsonParsingStats.toString());
    }

    /**
     * Creates and returns a list of tasks for processing each file. Each task involves parsing a single file
     * and updating the shared statistics object.
     *
     * @param statistic the statistic object to update during file processing.
     * @param fieldName the field name to extract data from within the JSON files.
     * @return a list of callable tasks for the executor service.
     */
    private List<Callable<Void>> getTasks(Statistic statistic, String fieldName) {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (File file : files) {
            tasks.add(() -> {
                StatisticJsonAdder adder = new StatisticJsonAdder(statistic, fieldName);
                jsonParsingStats.addAllFilesProcessed();

                try {
                    adder.addFieldValuesIntoStats(file);
                } catch (Exception e) {
                    logger.warn(String.format("Exception in stats making with file %s", file.getName()), e);
                    jsonParsingStats.addInvalidFiles();
                }
                jsonParsingStats.addTotalObjectsProcessed(adder.getTotalObjectLastFileProcessed());
                jsonParsingStats.addObjectsWithMistake(adder.getFailedObjectLastFileProcessed());

                return null;
            });
        }
        return tasks;
    }


    /**
     * The JsonFileStats class is designed to track and manage the statistics related to the processing of JSON files.
     * This includes counting the total number of objects processed, the number of objects with mistakes,
     * the number of invalid files, and the total number of files processed. Also this class is constructed for
     * a multi-threaded environment.
     */
    public static class JsonFileStats {
        private final AtomicInteger totalObjectsProcessed = new AtomicInteger(0);
        private final AtomicInteger objectsWithMistake = new AtomicInteger(0);
        private final AtomicInteger invalidFiles = new AtomicInteger(0);
        private final AtomicInteger allFilesProcessed = new AtomicInteger(0);

        /**
         * Increments the count of total objects processed by a specified amount.
         *
         * @param amount The number of objects processed to add to the total count.
         */
        public void addTotalObjectsProcessed(int amount) {
            totalObjectsProcessed.addAndGet(amount);
        }

        /**
         * Increments the count of total objects processed by one.
         */
        public void addTotalObjectsProcessed() {
            addTotalObjectsProcessed(1);
        }

        /**
         * Increments the count of objects with mistakes by a specified amount.
         *
         * @param amount The number of objects with mistakes to add to the total count.
         */
        public void addObjectsWithMistake(int amount) {
            objectsWithMistake.addAndGet(amount);
        }

        /**
         * Increments the count of objects with mistakes by one.
         */
        public void addObjectsWithMistake() {
            addObjectsWithMistake(1);
        }

        /**
         * Increments the count of invalid files by a specified amount.
         *
         * @param amount The number of invalid files to add to the total count.
         */
        public void addInvalidFiles(int amount) {
            invalidFiles.addAndGet(amount);
        }

        /**
         * Increments the count of invalid files by one.
         */
        public void addInvalidFiles() {
            addInvalidFiles(1);
        }

        /**
         * Increments the count of all files processed by a specified amount.
         *
         * @param amount The number of files processed to add to the total count.
         */
        public void addAllFilesProcessed(int amount) {
            allFilesProcessed.addAndGet(amount);
        }

        /**
         * Increments the count of all files processed by one.
         */
        public void addAllFilesProcessed() {
            addAllFilesProcessed(1);
        }

        /**
         * Generates a string representation of all statistics collected by this instance,
         * including the number of files successfully processed, the number of corrupted files,
         * and the details regarding objects processed and objects with errors.
         *
         * @return A string summarizing the collected statistics.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(String.format("Added to statistic all %d files", allFilesProcessed.get() - invalidFiles.get()));
            if (invalidFiles.get() !=0 ){
                sb.append(String.format(", %d files were corrupted", invalidFiles.get()));
            }
            sb.append(String.format(", total processed %d objects", totalObjectsProcessed.get() - objectsWithMistake.get()));
            if (objectsWithMistake.get() !=0 ){
                sb.append(String.format(", %d weren't have exact parameter", objectsWithMistake.get()));
            }
            return sb.toString();
        }

    }

}
