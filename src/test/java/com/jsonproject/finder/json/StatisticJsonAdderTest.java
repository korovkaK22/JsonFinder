package com.jsonproject.finder.json;
import static org.junit.jupiter.api.Assertions.*;

import com.jsonproject.finder.exceptions.IllegalJsonStructureException;
import com.jsonproject.finder.statistic.StatisticChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class StatisticJsonAdderTest {
    private static final Logger logger = LogManager.getLogger(StatisticJsonAdderTest.class);

    private StatisticJsonAdder adder;

    @BeforeEach
    private void setUp(){
        String parameter = "name";
        adder = new StatisticJsonAdder(StatisticChooser.getStatistic(parameter), parameter);
    }

    @Test
    public void testAddFieldValuesIntoStatsWithNullFile() {
        assertThrows(NullPointerException.class, () -> adder.addFieldValuesIntoStats(null));
    }

    @Test
    public void testAddFieldValuesIntoStatsWithBadJsonStructure() {

    }

    @ParameterizedTest
    @MethodSource("getJsonStructure")
    public void testProcessObjectsWithIllegalJson(String value, @TempDir Path tempDir) {

        String filename = String.format("%s/%s", tempDir, "driver.json");
        File file = new File(filename);

        generateFile(file, value);
        assertThrows(IllegalJsonStructureException.class, () -> adder.addFieldValuesIntoStats(file));


    }

    private void generateFile(File file, String value){
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(value);
        } catch (IOException e) {
            logger.error("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private static List<String> getJsonStructure() {
        List<String> result = new ArrayList<>();
        result.add("""
                [             
                }]
                """);

        result.add("""
                
                """);

        result.add("""
                [{
                  "name" : "Verlie,                  
                  "cars" : "Toyota Prius, Ford Mustang"
                }]
                """);

        result.add("""
                [{
                  "name" : "Verlie"
                  "companyName" : "Glover-Glover",                  
                }]
                """);

        return result;
    }

}
