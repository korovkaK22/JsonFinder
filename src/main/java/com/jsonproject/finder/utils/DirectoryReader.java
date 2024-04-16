package com.jsonproject.finder.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryReader {

    public static List<File> getAllFiles(File dirFile) {
        validateDir(dirFile);

        FilenameFilter jsonFilter = (dir, name) -> name.toLowerCase().endsWith(".json");
        File[] files = dirFile.listFiles(jsonFilter);
        return files != null ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>();
    }


    private static void validateDir(File dir) throws IllegalArgumentException, NullPointerException{
        if (dir == null){
            throw new NullPointerException("This directory is null!");
        }
        if (!dir.exists()) {
            throw new IllegalArgumentException("Can't find such directory: " + dir);
        }

        if (!dir.isDirectory()){
            throw new IllegalArgumentException("This isn't a directory: " + dir);
        }
    }

}