package com.jsonproject.finder.statistic;

import java.util.Arrays;

/**
 * The CarsStatistic class extends TextStatistic to handle statistics specifically for car data,
 * allowing multiple values to be added from a single string input by splitting the input on commas.
 *
 * @see TextStatistic
 */
public class CarsStatistic extends TextStatistic {

    /**
     * Adds values to the statistic from a single string, where multiple car names are expected to be separated by commas.
     * Each name is processed individually and added to the statistics with TextStatistic validation.
     *
     * @see TextStatistic
     * @param value the comma-separated string containing multiple car names.
     */
    @Override
    public void addValue(String value) {
        String[] splitValues = value.split(",\\s");
        Arrays.stream(splitValues).forEach(this::addValue);
    }


}
