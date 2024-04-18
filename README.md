# JSON FINDER

---

## Table of Contents
1. [General Information](#general-information)
2. [Application Launch](#application-launch)
3. [Code Review](#code-review)
4. [Unit Tests](#unit-tests)
5. [Efficiency Analysis of Threads](#efficiency-analysis-of-threads)


## General Information

The application, JSON Finder, is designed to parse JSON files utilizing multiple threads to expedite the process and 
generate specific parameter-based statistics. It primarily works with two entities: `Company` and `TaxiDriver`. 
In the current scope of this section, the focus is on the `TaxiDriver` entity, with its attributes being defined within the `TaxiDriver.java` file.

The application facilitates the concurrent processing of large JSON datasets, enabling efficient 
analysis and manipulation of the data related to taxi drivers.

Below is an example of the JSON structure that the application can parse and analyze, specifically tailored 
to demonstrate the data format for a taxi driver entity:
```json
[{
  "name" : "Verlie",
  "companyName" : "Glover-Glover",
  "age" : 36,
  "drivingExperience" : 2,
  "salary" : 9154,
  "cars" : "Toyota Prius, Ford Mustang"
}, {
  "name" : "Onie",
  "companyName" : "Ward-Ward",
  "age" : 23,
  "drivingExperience" : 2,
  "salary" : 5857,
  "cars" : "Chevrolet Impala, Kia Soul"
}]
```

One of the key features of this application is its ability to output a statistics file. This file contains the values 
for which statistics were gathered, detailing the frequency of occurrences across all parsed files. Furthermore, these
values are meticulously organized in descending order, providing a clear and concise view of the data for easy
analysis.
Below is an example of the XML structure that the application will provide as  the result of it's work by make
statistic of the `name` parameter:

```XML
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<statistics>
    <item>
        <value>Juana</value>
        <count>2</count>
    </item>
    <item>
        <value>Laurianne</value>
        <count>1</count>
    </item>
</statistics>
```

## Application Launch
The entry point of our program is the `JSONFinder.java` class. Upon execution, it accepts two parameters. 
The first parameter is the directory path that contains the files we intend to parse. The second parameter 
specifies the attributes which will be used to compile the statistics.

If the input data is incorrect, the program will not proceed with the execution. Instead, 
it will terminate and log an error. Additionally, the class includes modifiable settings 
such as `THREAD_AMOUNT`, `XML_STATS_PATH`, and `STATISTIC_FILE_NAME`. These settings allow the user
to adjust the number of threads that process the files and to specify the directory for saving the statistical files. 
These configurations are user-adjustable to accommodate different processing requirements and output destinations.

Below is an example of start arguments for processing testing data in resources folder and make statistic by cars:

```cmd
src/main/resources cars
```

## Code Review

## Unit Tests

## Efficiency Analysis of Threads
