package com.jsonproject.finder.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.jsonproject.finder.exceptions.IllegalJsonStructureException;
import com.jsonproject.finder.statistic.Statistic;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * The StatisticJsonAdder class is designed for extracting specific fields from JSON files and adding them to a statistic repository.
 * This class handles the reading of JSON files, identifies and extracts values associated with a specified field name, and processes
 * these values using an implementation of the {@link Statistic} interface.
 *
 * @see Statistic
 */
public class StatisticJsonAdder {
    /**
     * Logger for this class, used to log information and warnings during the processing of JSON files.
     */
    private static final Logger logger = LogManager.getLogger(StatisticJsonAdder.class);

    /**
     * An instance of Statistic used to record values extracted from the JSON file.
     */
    private final Statistic statistic;

    /**
     * The name of the field to extract from JSON objects within the JSON file.
     */
    private final String fieldName;

    /**
     * The file currently being processed by this instance.
     */
    File file;


    /**
     * Counter for all objects processed during the last file processing, including invalid objects.
     * This counter is reset with each new file processed.
     */
    @Getter
    private int failedObjectLastFileProcessed = 0;

    /**
     * Counter for only the invalid objects encountered during the last file processing.
     * This counter is reset with each new file processed.
     */
    @Getter
    private int totalObjectLastFileProcessed = 0;

    /**
     * Constructs a new StatisticJsonAdder with the specified statistic handler and field name.
     *
     * @param statistic the statistic implementation to use for adding field values.
     * @param fieldName the name of the field to look for in the JSON objects.
     */
    public StatisticJsonAdder(Statistic statistic, String fieldName) {
        this.statistic = statistic;
        this.fieldName = fieldName;
    }

    /**
     * Processes a JSON file, extracting values associated with {@code fieldName} and adding them to the statistic.
     * All JSON objects are expected to be in an array format at the root of the file.
     *
     * @param file the JSON file to process.
     * @throws IllegalJsonStructureException if the JSON file does not start with an array or if the structure is otherwise invalid.
     */
    public void addFieldValuesIntoStats(File file) throws IllegalJsonStructureException {
        try (JsonParser parser = createParser(file)) {
            this.file = file;
            processObjects(parser);
        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalJsonStructureException(String.format("File %s has invalid array structure", file.getName()), e);
        }
        //logResults(failedObjectLastFileProcessed, totalObjectLastFileProcessed, file);
    }

    /**
     * Creates a JSON parser for the given file.
     *
     * @param file the file to create a parser for.
     * @return a {@link JsonParser} for reading the file.
     * @throws IllegalJsonStructureException if an I/O error occurs while opening the file.
     */
    private JsonParser createParser(File file) throws IllegalJsonStructureException {
        JsonFactory factory = new JsonFactory();
        try {
            return factory.createParser(file);
        } catch (IOException | NullPointerException e) {
            throw new IllegalJsonStructureException("Illegal file structure", e);
        }
    }

    /**
     * Processes all JSON objects in the JSON array, updating statistics based on the presence and validity of the specified field.
     *
     * @param parser the JSON parser to use for reading the JSON objects.
     * @throws IOException if an error occurs while reading from the JSON file.
     */
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

    /**
     * Processes an individual JSON object, extracting and adding the value associated with {@code fieldName} to the statistic.
     *
     * @param parser the JSON parser to use for reading the object's fields.
     * @return true if the specified field was found and processed without error, false otherwise.
     * @throws IOException if an error occurs while reading from the JSON file.
     */
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

    /**
     * Logs the results of processing a file, including the number of successful and failed additions.
     *
     * @param failedObjectCount the number of objects that failed to process correctly.
     * @param allObject the total number of objects processed.
     * @param file the file that was processed.
     */
    private void logResults(int failedObjectCount, int allObject, File file) {
        if (failedObjectCount == 0) {
            logger.info(String.format("Successfully added from %s %d objects", file.getName(), allObject));
        } else {
            logger.warn(String.format("Successfully added from %s %d objects; %d objects haven't got exact parameter", file.getName(), allObject - failedObjectCount, failedObjectCount));
        }
    }
}

