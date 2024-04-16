package com.jsonproject.finder;

import com.jsonproject.finder.validator.ArgsValidator;
import com.jsonproject.finder.validator.ArgsValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonFinder {
    private static Logger logger = LogManager.getLogger(JsonFinder.class);


    public static void main(String[] args) {
        try {
            ArgsValidator argsValidator = new ArgsValidatorImpl();
            argsValidator.validate(args);
        } catch (Exception e) {
            logger.error("Program has started with illegal arguments, aborting", e);
            return;
        }



    }


}
