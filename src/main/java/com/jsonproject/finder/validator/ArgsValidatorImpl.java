package com.jsonproject.finder.validator;

import com.jsonproject.finder.entity.TaxiDriver;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ArgsValidatorImpl implements ArgsValidator{

    List<String> fieldNames ;


    @Override
    public void validate(String[] args) throws IllegalArgumentException {
        if (args.length <2){
            throw new IllegalArgumentException(String.format("Total args is less than 2 (%d)",args.length));
        }

        if (args.length >2){
            throw new IllegalArgumentException(String.format("Total args is greater than 2 (%d)",args.length));
        }

        validatePath(args[0]);
        validateParameter(args[1]);
    }



    public void validateParameter(String parameter)  throws IllegalArgumentException {
        fieldNames = new ArrayList<>(10);
        Field[] fields = TaxiDriver.class.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName().toLowerCase());
        }

        if (!fieldNames.contains(parameter.toLowerCase())) {
            throw new IllegalArgumentException("Can't find such parameter: " + parameter);
        }
    }


    public void validatePath(String path){
        File file = getFile(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("Can't find such directory: " + path);
        }

        if (!file.isDirectory()){
            throw new IllegalArgumentException("This isn't a directory: " + path);
        }
    }


    protected File getFile(String path){
        return new File(path);
    }

}
