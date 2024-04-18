package com.jsonproject.finder.statistic;

public class StatisticChooser {

    public static Statistic getStatistic(String parameter) {
        return switch (parameter.toLowerCase()) {
            case "cars" -> new CarsStatistic();
            case "age" -> new NumberStatistic(18, 100);
            case "drivingexperience" -> new NumberStatistic(0, 90);
            case "salary" -> new NumberStatistic(0, 10_000_000L);
            default -> new TextStatistic();
        };
    }
}
