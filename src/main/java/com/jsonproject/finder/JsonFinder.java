package com.jsonproject.finder;

import com.jsonproject.finder.statistic.Statistic;
import com.jsonproject.finder.statistic.StatisticChooser;
import com.jsonproject.finder.threads.ThreadStatsCounter;
import com.jsonproject.finder.utils.DirectoryReader;
import com.jsonproject.finder.utils.ArgsValidator;
import com.jsonproject.finder.xml.XmlStatistic;
import com.jsonproject.finder.xml.XmlWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * The JsonFinder class serves as the main entry point for a program that processes JSON files to gather statistical data,
 * which it then outputs in an XML format. The program handles command-line arguments to specify the directory of JSON files
 * and the specific statistic to calculate. It supports multithreaded processing of the JSON files and outputs the results
 * in XML format.
 */
public class JsonFinder {
    private static final Logger logger = LogManager.getLogger(JsonFinder.class);
    /**
     * Number of threads that will be used in future parsing
     */
    public static final int THREAD_AMOUNT = 8;
    /**
     * Path, where statistic XML file will be saved.
     */
    public static final String XML_STATS_PATH = "src/main/resources";


    /**
     * The main method that orchestrates the processing of JSON files and output of statistics in XML format.
     * It accepts command-line arguments to specify the directory containing JSON files and the type of statistic to gather.
     *
     * @param args The command-line arguments, expected to contain at least two elements:
     *             args[0] - The directory path where JSON files are located.
     *             args[1] - The statistic type to calculate.
     */
    public static void main(String[] args) {
        try {
            // Validate command-line arguments
            ArgsValidator argsValidator = new ArgsValidator();
            argsValidator.validate(args);
        } catch (Exception e) {
            logger.fatal("Program has started with illegal arguments, aborting", e);
            return;
        }
        logger.info("Validation passed, starting parsing files");

        // Read all JSON files from the specified directory
        List<File> files = DirectoryReader.getAllFiles(new File(args[0]));

        // Get the appropriate statistic based on the user's choice
        Statistic stats = StatisticChooser.getStatistic(args[1]);

        // Process files using a multithreaded counter
        ThreadStatsCounter counter = new ThreadStatsCounter(files, THREAD_AMOUNT);
        counter.addDataToStatisticFromFiles(stats, args[1]);

        // Convert statistics to XML format
        XmlStatistic xmlStatistic = new XmlStatistic(stats);

        try {
            // Write XML data to file
            XmlWriter writer = new XmlWriter(xmlStatistic);
            File result = new File(XML_STATS_PATH + String.format("/statistic_by_%s.xml", args[1].toLowerCase(Locale.ROOT)));
            boolean writingResult = writer.writeXmlStatsToXml(result);

            if (writingResult) {
                logger.info(String.format("File \"%s\" was successfully created.", result.getName()));
            } else {
                logger.error(String.format("File \"%s\" wasn't successfully created. Check logs for more info", result.getName()));
            }
        } catch (Exception e) {
            logger.fatal("Can't make xml file", e);
        }
    }
}