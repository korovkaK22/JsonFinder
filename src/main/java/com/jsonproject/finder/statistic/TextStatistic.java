package com.jsonproject.finder.statistic;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TextStatistic implements Statistic {

    protected final Map<String, Integer> stats = new ConcurrentHashMap<>();

    @Override
    public void addValue(String value) throws IllegalArgumentException, NullPointerException{
        if (value == null){
        throw new NullPointerException("Parameter is null");
        }
        if (value.trim().length() == 0){
            throw new IllegalArgumentException("Parameter is Empty");
        }
        stats.put(value, stats.getOrDefault(value, 0) + 1);
    }

    @Override
    public Map<String, Integer> getStatistic() {
        return Collections.unmodifiableMap(stats);
    }


}
