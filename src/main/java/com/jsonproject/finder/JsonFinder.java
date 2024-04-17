package com.jsonproject.finder;

import com.jsonproject.finder.statistic.Statistic;
import com.jsonproject.finder.statistic.StatisticFactory;
import com.jsonproject.finder.threads.ThreadStatsCounter;
import com.jsonproject.finder.utils.DirectoryReader;
import com.jsonproject.finder.utils.validator.ArgsValidator;
import com.jsonproject.finder.utils.validator.ArgsValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class JsonFinder {
    private static final Logger logger = LogManager.getLogger(JsonFinder.class);
    private static final int THREAD_AMOUNT = 2;


    public static void main(String[] args) {

        try {
            ArgsValidator argsValidator = new ArgsValidatorImpl();
            argsValidator.validate(args);
        } catch (Exception e) {
            logger.fatal("Program has started with illegal arguments, aborting", e);
            return;
        }



        List<File> files = DirectoryReader.getAllFiles(new File(args[0]));

        Statistic stats = new StatisticFactory().getStatistic(args[1]);
        ThreadStatsCounter counter = new ThreadStatsCounter(files, THREAD_AMOUNT);
        counter.makeStatistic(stats, args[1]);




    }


}
