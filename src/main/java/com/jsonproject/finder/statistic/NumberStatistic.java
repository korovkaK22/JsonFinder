package com.jsonproject.finder.statistic;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The NumberStatistic class implements the Statistic interface for handling numerical data.
 * It manages statistics about numeric values parsed from strings, validating against specified minimum and maximum bounds.
 *
 * @see Statistic
 */
@NoArgsConstructor
public class NumberStatistic implements Statistic {

    /**
     * Stores numeric values as keys in String format and their occurrence counts as values.
     */
    protected final Map<String, Integer> stats = new ConcurrentHashMap<>();

    /**
     * The minimum allowable value for entries in this statistic.
     */
    private long minValue = Long.MIN_VALUE;
    /**
     * The maximum allowable value for entries in this statistic.
     */
    private long maxValue = Long.MAX_VALUE;


    /**
     * Constructs a NumberStatistic with specified minimum and maximum values.
     *
     * @param minValue the minimum value to be considered valid.
     * @param maxValue the maximum value to be considered valid.
     */
    public NumberStatistic(long minValue, long maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Adds a numeric value to the statistic after validating it.
     * If the value is valid, it increments its count in the stats map.
     *
     * @param value the value to add.
     */
    @Override
    public void addValue(String value) {
        validate(value);
        stats.put(value, stats.getOrDefault(value, 0) + 1);
    }

    /**
     * Validates that the provided string can be parsed as a long and is within the allowed range.
     *
     * @param value the string to validate.
     * @throws IllegalArgumentException if the value is out of bounds or not a valid long.
     */
    private void validate(String value) {
        long number;
        try {
            number = Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid number value: %s", value));
        }
        if (number < minValue) {
            throw new IllegalArgumentException(String.format("Number %d is less than min value %d", number, minValue));
        }
        if (number > maxValue) {
            throw new IllegalArgumentException(String.format("Number %d is greater than max value %d", number, maxValue));
        }
    }

    /**
     * Returns an unmodifiable view of the statistics map.
     *
     * @return the map of values and their occurrence counts.
     */
    @Override
    public Map<String, Integer> getStatistic() {
        return Collections.unmodifiableMap(stats);
    }
}
