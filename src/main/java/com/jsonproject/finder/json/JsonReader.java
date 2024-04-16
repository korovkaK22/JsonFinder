package com.jsonproject.finder.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.jsonproject.finder.statistic.Statistic;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;


public class JsonReader {
    private static final Logger logger = LogManager.getLogger(JsonReader.class);
    private final Statistic statistic;
    private final String fieldName;
    int failedObjectCount = 0;
    int allObject = 0;

    public JsonReader(Statistic statistic, String fieldName) {
        this.statistic = statistic;
        this.fieldName = fieldName;
    }

    public void addFieldValuesIntoStats(File file) throws IOException {
        try (JsonParser parser = createParser(file)) {
            processObjects(parser);
        }
        logResults(failedObjectCount, allObject, file);
    }

    private JsonParser createParser(File file) throws IOException {
        JsonFactory factory = new JsonFactory();
        return factory.createParser(file);
    }

    private void processObjects(JsonParser parser) throws IOException {
        failedObjectCount = 0;
        allObject = 0;

        if (parser.nextToken() == JsonToken.START_ARRAY) {
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                allObject++;
                if (!processObject(parser)) {
                    failedObjectCount++;
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

