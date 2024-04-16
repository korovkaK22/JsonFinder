package com.jsonproject.finder.statistic;

import java.util.Arrays;

public class CarsStatistic extends Statistic{

    @Override
    public void addValue(String value){

        String[] splitValues = value.split(",\\s");
        Arrays.stream(splitValues).forEach(this::addCorrectedValue);
    }

    private void addCorrectedValue(String value){
        stats.put(value, stats.getOrDefault(value, 0) + 1);
    }
}
