package com.jsonproject.finder.statistic;

/**
 * The StatisticChooser class provides a method to select the appropriate Statistic instance
 * based on a given parameter. This class acts as a factory, utilizing a switch statement
 * to determine which specific Statistic subclass to instantiate and return.
 *
 * Each type of Statistic is tailored to handle different types of data, such as numeric data for ages or salaries,
 * and text data for general categories like cars or default text entries.
 */
public class StatisticChooser {

    /**
     * Returns an instance of Statistic corresponding to the provided parameter. This method uses
     * the parameter to decide which type of Statistic object to create, supporting various data types and
     * categories like cars, age, driving experience, and salary. If the parameter does not match
     * any predefined categories, it defaults to a general text statistic.
     *
     * @param parameter A string indicating the type of statistic to be returned. The method handles
     *                  this parameter case-insensitively.
     * @return Statistic An instance of a class implementing the Statistic interface, specialized to the
     *                   type of data indicated by the parameter.     *
     * <ul>
     *     <li>"cars" - Returns a CarsStatistic for managing statistics related to car data.</li>
     *     <li>"age" - Returns a NumberStatistic set with age-appropriate boundaries (18 to 100 years).</li>
     *     <li>"drivingexperience" - Returns a NumberStatistic designed to handle driving experience data
     *                               from 0 to 90 years.</li>
     *     <li>"salary" - Returns a NumberStatistic to manage salary data with a range from 0 to 10,000,000.</li>
     *     <li>default - Returns a TextStatistic for general text-based data handling.</li>
     * </ul>
     */
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
