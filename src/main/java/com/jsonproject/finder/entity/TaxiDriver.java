package com.jsonproject.finder.entity;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TaxiDriver {
    private String name;
    private String companyName;
    private int age;
    private int drivingExperience;
    private long salary;
    private String cars;
}
