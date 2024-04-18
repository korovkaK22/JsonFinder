package com.jsonproject.finder.statistic;

import java.util.Map;

/**
 * The Statistic interface defines the methods required to add and retrieve values to/from a statistical data collection.
 * Implementing classes can use this interface to manage statistical data which involves counting occurrences of various values.
 * <p>
 * The main purpose of this interface is to provide a common protocol for objects that collect statistical data
 * such as counts of different values that are found during processing of data sets.
 */
public interface Statistic {

    /**
     * Adds a value to the statistic collection. If the value already exists in the collection,
     * it increments the count associated with this value. If the value does not exist,
     * it initializes its count.
     *
     * @param value the value to be added or incremented in the statistic collection.
     * @throws IllegalArgumentException if the provided value is not valid for the statistic being calculated.
     * @throws NullPointerException     if the provided value is null, assuming the implementation does not support null values.
     */
    void addValue(String value) throws IllegalArgumentException, NullPointerException;

    /**
     * Retrieves the entire statistic as a map where each key is a value that has been added
     * and each corresponding value is the count of occurrences of the key.
     *
     * @return a unmodifiable map representing the collected statistical data.
     */
    Map<String, Integer> getStatistic();
}
