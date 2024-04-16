package com.jsonproject.finder.entities;

import lombok.*;

import java.util.List;

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
