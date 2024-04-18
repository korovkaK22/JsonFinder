package com.jsonproject.finder.statistic;

import java.util.Arrays;

public class CarsStatistic extends TextStatistic {

    @Override
    public void addValue(String value){
        String[] splitValues = value.split(",\\s");
        Arrays.stream(splitValues).forEach(this::addValue);
    }


}
