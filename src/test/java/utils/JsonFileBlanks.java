package utils;

import java.util.Random;

public class JsonFileBlanks {

    public static void createLongFile(String path, boolean isCorrupted){
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(new Random(13));
        TaxiDriverJsonFileCreator filesCreator = new TaxiDriverJsonFileCreator(300, taxiCreator, isCorrupted);
        filesCreator.generateFile(path);
    }

    public static void createLongFile(String path){
        createLongFile(path, false);
    }

    public static void createFile(String path, boolean isCorrupted){
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(new Random(13));
        TaxiDriverJsonFileCreator filesCreator = new TaxiDriverJsonFileCreator(300, taxiCreator, isCorrupted);
        filesCreator.generateFile(path);
    }

    public static void createFile(String path){
        createFile(path, false);
    }


}
