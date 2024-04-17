package utils;

import java.util.Random;

public class JsonFileCreatingBlanks {

    public static void createLongFile(Random random, String path, boolean isCorrupted){
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(random);
        TaxiDriverJsonFileCreator filesCreator = new TaxiDriverJsonFileCreator(30000, taxiCreator, isCorrupted, random);
        filesCreator.generateFile(path);
    }

    public static void createLongFile(Random random, String path){
        createLongFile(random, path, false);
    }

    public static void createFile(Random random, String path, boolean isCorrupted){
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(random);
        TaxiDriverJsonFileCreator filesCreator = new TaxiDriverJsonFileCreator(300, taxiCreator, isCorrupted, random);
        filesCreator.generateFile(path);
    }

    public static void createFile(Random random, String path){
        createFile(random, path, false);
    }


}
