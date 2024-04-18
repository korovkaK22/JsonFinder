package com.jsonproject.finder.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The TaxiDriver class represents an entity capturing details about a taxi driver.
 */
@NoArgsConstructor
@Getter
public class TaxiDriver {
    private String name;
    private String companyName;
    private int age;
    private int drivingExperience;
    private long salary;
    private String cars;

    /**
     * Constructs a TaxiDriver instance ensuring all parameters meet the required criteria for validity.
     *
     * @param name The name of the taxi driver, cannot be null or empty.
     * @param companyName The name of the company employing the taxi driver, cannot be null or empty.
     * @param age The age of the taxi driver, must be between 18 and 100 inclusive.
     * @param drivingExperience The years of driving experience, must be between 0 and 90 inclusive.
     * @param salary The annual salary, must be between 0 and 10,000,000 inclusive.
     * @param cars A comma-separated string of cars, cannot be null or empty.
     * @throws IllegalArgumentException if any parameter does not meet its validation criteria.
     * @throws NullPointerException if any String parameter is null.
     */
    public TaxiDriver(String name, String companyName, int age, int drivingExperience, long salary, String cars) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null.");
        }
        if (companyName == null) {
            throw new NullPointerException("Company name cannot be null.");
        }
        if (cars == null) {
            throw new NullPointerException("Cars list cannot be null.");
        }

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty.");
        }
        if (age < 18 || age > 100) {
            throw new IllegalArgumentException("Age must be between 18 and 100.");
        }
        if (drivingExperience < 0 || drivingExperience > 90) {
            throw new IllegalArgumentException("Driving experience must be between 0 and 90 years.");
        }
        if (salary < 0 || salary > 10_000_000) {
            throw new IllegalArgumentException("Salary must be between 0 and 10,000,000.");
        }
        if (cars.trim().isEmpty()) {
            throw new IllegalArgumentException("Cars list cannot be empty.");
        }


        this.name = name.trim();
        this.companyName = companyName.trim();
        this.age = age;
        this.drivingExperience = drivingExperience;
        this.salary = salary;
        this.cars = cars.trim();
    }


    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name.trim();
    }



    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty.");
        }
        this.companyName = companyName.trim();
    }


    public void setAge(int age) {
        if (age < 18 || age > 100) {
            throw new IllegalArgumentException("Age must be between 18 and 100.");
        }
        this.age = age;
    }


    public void setDrivingExperience(int drivingExperience) {
        if (drivingExperience < 0 || drivingExperience > 90) {
            throw new IllegalArgumentException("Driving experience must be between 0 and 90 years.");
        }
        this.drivingExperience = drivingExperience;
    }


    public void setSalary(long salary) {
        if (salary < 0 || salary > 10_000_000) {
            throw new IllegalArgumentException("Salary must be between 0 and 10,000,000.");
        }
        this.salary = salary;
    }


    public void setCars(String cars) {
        if (cars == null || cars.trim().isEmpty()) {
            throw new IllegalArgumentException("Cars list cannot be null or empty.");
        }
        this.cars = cars.trim();
    }
}