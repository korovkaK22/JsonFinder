package com.jsonproject.finder;

import com.jsonproject.finder.statistic.Statistic;
import com.jsonproject.finder.statistic.StatisticFactory;
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

public class JsonFinder {
    private static final Logger logger = LogManager.getLogger(JsonFinder.class);
    public static final int THREAD_AMOUNT = 8;
    public static final String XML_STATS_PATH = "src/main/resources";


    public static void main(String[] args) {

        try {
            ArgsValidator argsValidator = new ArgsValidator();
            argsValidator.validate(args);
        } catch (Exception e) {
            logger.fatal("Program has started with illegal arguments, aborting", e);
            return;
        }
        logger.info("Validation passed, starting parsing files");

        List<File> files = DirectoryReader.getAllFiles(new File(args[0]));

        Statistic stats = new StatisticFactory().getStatistic(args[1]);
        ThreadStatsCounter counter = new ThreadStatsCounter(files, THREAD_AMOUNT);
        counter.initStatisticFromFiles(stats, args[1]);

        XmlStatistic xmlStatistic = new XmlStatistic(stats);

        try {
            XmlWriter writer = new XmlWriter(xmlStatistic);
            File result = new File(
                    XML_STATS_PATH + String.format("/statistic_by_%s.xml", args[1].toLowerCase(Locale.ROOT)));
            boolean writingResult = writer.writeXmlStatsToXml(result);
            if (writingResult) {
                logger.info(String.format("File \"%s\" was successfully created.", result.getName()));
            } else {
                logger.info(String.format("File \"%s\" wasn't successfully created. Check logs for more info", result.getName()));
            }
        } catch (Exception e) {
            logger.fatal("Can't make xml file", e);
        }


    }


}
