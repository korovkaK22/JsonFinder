package com.jsonproject.finder.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TaxiDriver {
    private String name;
    private String company;
    private int age;
    private int drivingExperience;
    private long salary;
    private List<String> cars;
}
