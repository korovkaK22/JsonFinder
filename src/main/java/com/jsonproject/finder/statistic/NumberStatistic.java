package com.jsonproject.finder.statistic;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class NumberStatistic implements Statistic {

    protected final Map<String, Integer> stats = new ConcurrentHashMap<>();

    /* minimal long value, inclusive */
    private long minValue = Long.MIN_VALUE;
    /* maximal long value, inclusive */
    private long maxValue = Long.MAX_VALUE;

    public NumberStatistic(long minValue, long maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void addValue(String value) {
        validate(value);
        stats.put(value, stats.getOrDefault(value, 0) + 1);
    }

    private void validate(String value){
        long number;
        try {
            number = Long.parseLong(value);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(String.format("Invalid number value: %s", value));
        }
        if (number < minValue) {
            throw new IllegalArgumentException(String.format("Number %d is less than min value %d", number, minValue));
        }
        if (number > maxValue) {
            throw new IllegalArgumentException(String.format("Number %d is greater than max value %d", number, maxValue));
        }
    }

    @Override
    public Map<String, Integer> getStatistic() {
        return Collections.unmodifiableMap(stats);
    }
}
