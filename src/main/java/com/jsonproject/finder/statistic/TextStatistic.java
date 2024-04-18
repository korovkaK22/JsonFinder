package com.jsonproject.finder.statistic;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The TextStatistic class implements the Statistic interface for handling textual data.
 * It manages statistics about text strings, ensuring that only non-null and non-empty strings are counted.
 *
 * @see Statistic
 */
public class TextStatistic implements Statistic {

    /**
     * Stores text values as keys and their occurrence counts as values.
     */
    protected final Map<String, Integer> stats = new ConcurrentHashMap<>();

    /**
     * Adds a text value to the statistic after validating it.
     * If the value is valid, it increments its count in the stats map.
     *
     * @param value the text to add.
     * @throws IllegalArgumentException if the text is empty.
     * @throws NullPointerException     if the text is null.
     */
    @Override
    public void addValue(String value) {
        validate(value);
        stats.put(value, stats.getOrDefault(value, 0) + 1);
    }

    /**
     * Validates that the provided text is neither null nor empty.
     *
     * @param value the string to validate.
     * @throws NullPointerException     if the value is null.
     * @throws IllegalArgumentException if the value is empty.
     */
    private void validate(String value) {
        if (value == null) {
            throw new NullPointerException("Parameter is null");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("Parameter is Empty");
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
