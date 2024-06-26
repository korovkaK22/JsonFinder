package utils.jsoncreation;

import com.jsonproject.finder.entity.TaxiDriver;

import java.util.List;
import java.util.Random;

public class JsonFileCreatingBlanks {

    public static void createFile(Random random, String path, int objectsAmount){
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(random);
        TaxiDriverJsonFileCreator filesCreator = new TaxiDriverJsonFileCreator();
        filesCreator.generateFile(path, taxiCreator, objectsAmount);
    }



    public static void createFile(String path, List<TaxiDriver> driverList){
        TaxiDriverJsonFileCreator filesCreator = new TaxiDriverJsonFileCreator();
        filesCreator.generateFile(path, driverList);
    }


}
