package com.jsonproject.finder.utils.validator;

import com.jsonproject.finder.entity.TaxiDriver;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ArgsValidatorImpl implements ArgsValidator{

    List<String> fieldNames = new ArrayList<>();

    {
        Field[] fields = TaxiDriver.class.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName().toLowerCase());
        }
    }


    @Override
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

     public void validateParameter(String parameter)  throws IllegalArgumentException {

        if (!fieldNames.contains(parameter.toLowerCase())) {
            throw new IllegalArgumentException("Can't find such parameter: " + parameter);
        }
    }


    public void validatePath(File dir) throws IllegalArgumentException{
        if (!dir.exists()) {
            throw new IllegalArgumentException("Can't find such directory: " + dir);
        }

        if (!dir.isDirectory()){
            throw new IllegalArgumentException("This isn't a directory: " + dir);
        }
    }

}
