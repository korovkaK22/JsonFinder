package com.jsonproject.finder.entity;

import lombok.*;


/**
 * Just added it, but it doesn't be used anywhere in the code, possibly schema for next blocks
 */
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Company {
 String name;
 int employeesAmount;
 int workingCarsAmount;

}
