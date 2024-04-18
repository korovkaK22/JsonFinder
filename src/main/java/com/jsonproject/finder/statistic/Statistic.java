package com.jsonproject.finder.statistic;

import java.util.Map;

public interface Statistic {

    void addValue(String value);

    Map<String, Integer> getStatistic() throws IllegalArgumentException, NullPointerException;
}
