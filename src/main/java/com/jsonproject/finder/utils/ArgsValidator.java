package com.jsonproject.finder.utils;

import com.jsonproject.finder.entity.TaxiDriver;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for validating arguments provided to the application.
 * This class checks the validity of the directory paths and parameters specified by the user.
 */
public class ArgsValidator{

    /**
     * Stored valid parameter names based on the fields of the TaxiDriver class.
     */
    List<String> fieldNames = new ArrayList<>();


    {
        Field[] fields = TaxiDriver.class.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName().toLowerCase());
        }
    }


    /**
     * Validates the array of arguments provided to the application.
     *
     * @param args The command-line arguments to validate.
     * @throws IllegalArgumentException if the number of arguments is not exactly 2, or if the arguments do not meet the specified criteria.
     */
    public void validate(String[] args) throws IllegalArgumentException {
        if (args.length <2){
            throw new IllegalArgumentException(String.format("Total args is less than 2 (%d)",args.length));
        }

        if (args.length >2){
            throw new IllegalArgumentException(String.format("Total args is greater than 2 (%d)",args.length));
        }

        validatePath(new File (args[0]));
        validateParameter(args[1]);
    }

    /**
     * Validates that the specified parameter is a valid field name of the TaxiDriver class.
     *
     * @param parameter The parameter to validate.
     * @throws IllegalArgumentException if the parameter does not match any field name in the TaxiDriver class.
     */
     public void validateParameter(String parameter)  throws IllegalArgumentException {

        if (!fieldNames.contains(parameter.toLowerCase())) {
            throw new IllegalArgumentException("Can't find such parameter: " + parameter);
        }
    }

    /**
     * Validates that the specified directory exists and is indeed a directory.
     *
     * @param dir The directory to validate.
     * @throws IllegalArgumentException if the file is not a directory or does not exist.
     */
    public void validatePath(File dir) throws IllegalArgumentException{
        if (!dir.exists()) {
            throw new IllegalArgumentException("Can't find such directory: " + dir);
        }

        if (!dir.isDirectory()){
            throw new IllegalArgumentException("This isn't a directory: " + dir);
        }
    }

}
