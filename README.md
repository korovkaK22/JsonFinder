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

Comprehensive documentation has been also created for the entire codebase, which describes each field, method, and class. 
This documentation details the functionality of each component and outlines the dependencies it utilizes.

In keeping with the principle of `KISS` (_Keep It Simple, Stupid_), interfaces were not incorporated into this project. 
This decision was made under the understanding that the project would be completed with its current scope and there are
no plans for future expansion, thus a monolithic architecture was deemed suitable.


_Below is an example of the JSON structure that the application can parse and analyze, specifically tailored 
to demonstrate the data format for a taxi driver entity:_
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

_Below is an example of the XML structure that the application will provide as  the result of it's work by make
statistic of the `name` parameter:_

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
In this section, we will delve into the code structure and the logic behind the parsing operations. 
### ArgsValidator
The process begins with parameter validation in the `ArgsValidator` class. If the parameters are invalid, 
for instance, if the directory path is actually a file, the program will not initiate. The program also inspects, 
case-insensitively, whether the second parameter exists as an attribute of the taxi driver; hence, gathering
information on a non-existent field is not feasible.

### DirectoryReader
Following validation, the collection of all JSON files within the directory is performed using the `DirectoryReader` 
utility class. The next step is to select the correct implementation of the statistics interface to save the information.

### Statistic and StatisticChooser
After we need to choose the implementation of the `Statistic` interface, because all the processing logic and parameter's 
validation is different.
For this, we have the `StatisticChooser` class, which employs the strategy pattern to select the proper 
implementation of our statistics depending on parameter. 

In the statistics, the primary validation occurs when adding parameters to the statistics implementation. For instance,
for strings, the string should not be null or of zero length. For numbers, we can set a minimum and maximum threshold, 
depending on exact parameter.
The same validation applies to cars' names as it does for strings.

### ThreadStatsCounter and StatisticJsonAdder
Following the selection of the appropriate statistics implementation, we create a `ThreadStatsCounter` object. 
This object facilitates multithreaded reading of libraries using the `StatisticJsonAdder` class, which is responsible 
for parsing the files. The key advantage of `StatisticJsonAdder` is its ability to process corrupted files. It logs
the error yet continues to operate. It features robust validation and performs line-by-line reading, which prevents
loading the entire file into memory—a vital consideration for large files.

### XmlStatistic and XmlWriter
Next, we pass our statistical data to the `XmlStatistic` class, which enables us to translate our statistics 
into XML format and sort them in descending order automatically. Finally, this XML statistic is passed to
the `XmlWriter`, which writes out the final file.


## Unit Tests
As previously mentioned, unit tests have been implemented to validate the core components of the application, 
particularly the parsing logic and the formation of statistics.

There is a unit test `ArgsValidatorTest` in place for validating input data to ensure only correct parameters are accepted. 
There is also a unit test `StatisticJsonAdderTest` dedicated to the handling of invalid JSON files, ensuring that the class
`StatisticJsonAdder` can gracefully manage and log errors without interruption. There is a `XmlWriterTest` also for checking
cases when the file can't be made, or it has private access. Additionally, the correctness of the XML statistics
generation is validated through the `JSONFinderTest`.

Furthermore, supplementary classes have been also created, leveraging the Faker library, which allows for 
the generation of realistic-looking taxi driver data. This data can be written to JSON files or be directly compiled into 
statistic and then compared with the statistics generated by our application from 
the same JSON files. This comprehensive testing strategy ensures the application's functionality is thoroughly 
verified against real-world-like data, confirming the accuracy and reliability of our application. All this features 
is used in `JsonFinderTest`.


## Efficiency Analysis of Threads
Given that the application features multithreading for file processing, we can establish a table to illustrate how
effectively multithreading facilitates data processing.

_Below is a comparison table for processing either 40 objects per file or 400 objects per file, also considering the
total number of files used in the test—either 40 or 400—and varying the number of threads:_ 

<p align="center">
  <img src="https://i.imgur.com/hIkP6a1.png" alt="Table photo"/>
</p>
As we can infer from the table,A single thread operates almost eight times slower 
than two threads, but eight threads perform 30% faster than two threads. 
The advantage of using a large number of threads is evident when processing a large number of files. However, the time
spent switching the thread from one task to another takes more time than the actual data processing by the thread.
Therefore, we can conclude that even using two threads instead of one can speed up the execution of the code several times.


For those who require additional measurements, there
is the `ThreadTest` class which encapsulates the logic necessary to simulate varying thread counts for processing
different quantities of files. All these parameters are configurable and transferred to separate variables
`OBJECTS_IN_ONE_FILE` and `FILES_AMOUNT`, enabling effortless testing and visualization
of results.

_Below is a screenshot of processing 400 files with 400 objects per file with different tread's amount:_

<p align="center">
  <img src="https://i.imgur.com/lb3iTza.png" alt="Table photo"/>
</p>
