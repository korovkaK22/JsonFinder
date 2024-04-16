package com.jsonproject.finder.statistic;

public class StatisticFactory {

    public Statistic getStatistic(String parameter){
        if ("cars".equalsIgnoreCase(parameter)){
            return new CarsStatistic();
        } else {
            return new Statistic();
        }
    }
}
