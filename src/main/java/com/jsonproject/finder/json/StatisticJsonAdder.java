package com.jsonproject.finder.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.jsonproject.finder.exceptions.IllegalJsonStructureException;
import com.jsonproject.finder.statistic.Statistic;
import com.jsonproject.finder.statistic.TextStatistic;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;


public class StatisticJsonAdder {
    private static final Logger logger = LogManager.getLogger(StatisticJsonAdder.class);
    private final Statistic statistic;
    private final String fieldName;
    File file;
    @Getter
    /* Amount of all objects processed, including invalid, resets with a new file processing */
    int failedObjectLastFileProcessed = 0;
    @Getter
    /* Amount of only invalid appeared objects, resets with a new file processing */
    int totalObjectLastFileProcessed = 0;

    public StatisticJsonAdder(Statistic statistic, String fieldName) {
        this.statistic = statistic;
        this.fieldName = fieldName;
    }

    public void addFieldValuesIntoStats(File file) throws IllegalJsonStructureException, IOException {
        try (JsonParser parser = createParser(file)) {
            this.file = file;
            processObjects(parser);
        } catch (IllegalArgumentException e) {
            throw new IllegalJsonStructureException(String.format("File %s has invalid array structure", file.getName()), e);
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

        if (parser.nextToken() == JsonToken.START_ARRAY) {
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                totalObjectLastFileProcessed++;

                if (!processObject(parser)) {
                    failedObjectLastFileProcessed++;
                }

            }
        } else {
            throw new IllegalArgumentException("File didn't start with array.");
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
                try {
                    statistic.addValue(value);
                } catch (IllegalArgumentException e){
                    logger.warn(String.format("Can't add parameter %s to %s statistic from %s", value, fieldName, file.getName()), e);
                    failedObjectLastFileProcessed++;
                }  catch (NullPointerException e){
                    logger.warn("Null parameter, aborting");
                    failedObjectLastFileProcessed++;
                }

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

