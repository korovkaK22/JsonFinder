package com.jsonproject.finder;

import com.jsonproject.finder.entity.TaxiDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.JsonFileCreatingBlanks;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonFinderTest {

    private static final List<String> fields = new ArrayList<>(10);


    @BeforeAll
    static void setupAll() {
        Field[] temp = TaxiDriver.class.getDeclaredFields();
        for (Field field : temp) {
            fields.add(field.getName());
        }
    }


    @Test
    void mainTest() {

    }

    @Test
    void oneLongValidFile() {
        String path = "src/test/resources/long_file/drivers.json";
        File file = new File(path);
        if (!(file.exists())) {
            JsonFileCreatingBlanks.createLongFile(new Random(13), path);
        }

        JsonFinder.main(new String[]{file.getParent(), fields.get(0)});
        //todo перевірку статистики
    }

    @Test
    void coupleShortValidFile() {
        String folderPath = "src/test/resources/couple_files";
        File folder = new File(folderPath);
        if (!folder.exists()){
            folder.mkdir();
        }
        Random random = new Random(13);
        for (int i=1; i <= 30; i++){
            String path =String.format("%s/drivers_%d.json", folderPath, i);
            File file = new File(path);
            if (!(file.exists())) {
                JsonFileCreatingBlanks.createFile(new Random(random.nextInt(0, 100)), path, true);
            }
        }

        JsonFinder.main(new String[]{folderPath, fields.get(0)});
        //todo перевірку статистики

    }

    @Test
    void coupleLongValidFile() {
        String folderPath = "src/test/resources/couple_long_files";
        File folder = new File(folderPath);
        if (!folder.exists()){
            folder.mkdir();
        }
        Random random = new Random(13);
        for (int i=1; i <= 30; i++){
            String path =String.format("%s/drivers_%d.json", folderPath, i);
            File file = new File(path);
            if (!(file.exists())) {
                JsonFileCreatingBlanks.createLongFile(new Random(random.nextInt(0, 100)), path);
            }
        }

        JsonFinder.main(new String[]{folderPath, "drivingexperience"});
        //todo перевірку статистики

    }


}
