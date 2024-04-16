package com.jsonproject.finder;

import com.jsonproject.finder.utils.DirectoryReader;
import com.jsonproject.finder.utils.validator.ArgsValidator;
import com.jsonproject.finder.utils.validator.ArgsValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class JsonFinder {
    private static final Logger logger = LogManager.getLogger(JsonFinder.class);


    public static void main(String[] args) {

        try {
            ArgsValidator argsValidator = new ArgsValidatorImpl();
            argsValidator.validate(args);
        } catch (Exception e) {
            logger.error("Program has started with illegal arguments, aborting", e);
            return;
        }


        List<File> files = DirectoryReader.getAllFiles(new File(args[0]));



    }


}
