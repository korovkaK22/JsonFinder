package com.jsonproject.finder.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Utility class for reading directories and filtering out files based on specified criteria.
 */
public class DirectoryReader {
    /**
     * Retrieves all files from the specified directory that end with ".json".
     *
     * @param dirFile The directory from which files are to be listed.
     * @return A list of File objects representing the JSON files found in the specified directory.
     * @throws NullPointerException if the directory file object is null.
     * @throws IllegalArgumentException if the directory does not exist or is not a directory.
     */
    public static List<File> getAllFiles(File dirFile) throws IllegalArgumentException, NullPointerException {
        validateDir(dirFile);

        FilenameFilter jsonFilter = (dir, name) -> name.toLowerCase().endsWith(".json");
        File[] files = dirFile.listFiles(jsonFilter);
        return files != null ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>();
    }

    /**
     * Validates that the provided File object is a valid directory.
     *
     * @param dir The directory to validate.
     * @throws NullPointerException if the directory reference is null.
     * @throws IllegalArgumentException if the directory does not exist or is not actually a directory.
     */
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