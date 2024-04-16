package com.jsonproject.finder.statistic;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Statistic {
    Map<String, Integer> stats = new ConcurrentHashMap<>();

    public void addValue(String value){
        stats.put(value, stats.getOrDefault(value, 0) + 1);
    }


}
