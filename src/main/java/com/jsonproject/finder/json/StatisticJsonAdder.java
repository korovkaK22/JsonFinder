package com.jsonproject.finder.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.jsonproject.finder.statistic.Statistic;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;


public class StatisticJsonAdder {
    private static final Logger logger = LogManager.getLogger(StatisticJsonAdder.class);
    private final Statistic statistic;
    private final String fieldName;
    @Getter
    /* Amount of all objects processed, including invalid */
    int failedObjectLastFileProcessed = 0;
    @Getter
    /* Amount of invalid appeared objects */
    int totalObjectLastFileProcessed = 0;

    public StatisticJsonAdder(Statistic statistic, String fieldName) {
        this.statistic = statistic;
        this.fieldName = fieldName;
    }

    public void addFieldValuesIntoStats(File file) throws IOException {
        try (JsonParser parser = createParser(file)) {
            processObjects(parser);
        }
        logResults(failedObjectLastFileProcessed, totalObjectLastFileProcessed, file);
    }

    private JsonParser createParser(File file) throws IOException {
        JsonFactory factory = new JsonFactory();
        return factory.createParser(file);
    }

    private void processObjects(JsonParser parser) throws IOException {
        failedObjectLastFileProcessed = 0;
        totalObjectLastFileProcessed = 0;

        //todo написать, що якщо не масив початок, то екзепшн кине
        if (parser.nextToken() == JsonToken.START_ARRAY) {
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                totalObjectLastFileProcessed++;

                if (!processObject(parser)) {
                    failedObjectLastFileProcessed++;
                }

            }
        }
    }

    private boolean processObject(JsonParser parser) throws IOException {
        boolean foundField = false;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (foundField) continue;

            String currentName = parser.getCurrentName();
            if (fieldName.equalsIgnoreCase(currentName)) {
                parser.nextToken();
                String value = parser.getText();
                statistic.addValue(value);
                foundField = true;
            }
        }
        return foundField;
    }

    private void logResults(int failedObjectCount, int allObject, File file) {
        if (failedObjectCount == 0) {
            logger.info(String.format("Successfully added from %s %d objects", file.getName(), allObject));
        } else {
            logger.warn(String.format("Successfully added from %s %d objects; %d objects haven't got exact parameter", file.getName(), allObject - failedObjectCount, failedObjectCount));
        }
    }
}

