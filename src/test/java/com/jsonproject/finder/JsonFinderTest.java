package com.jsonproject.finder;

import com.jsonproject.finder.entity.TaxiDriver;
import org.junit.jupiter.api.Test;
import utils.TaxiDriverCreator;
import utils.TaxiDriverJsonFileCreator;

import java.util.Random;

public class JsonFinderTest {

    @Test
    void mainTest(){
        TaxiDriverCreator taxiCreator = new TaxiDriverCreator(new Random(13));
        TaxiDriverJsonFileCreator filesCreator = new TaxiDriverJsonFileCreator(30, taxiCreator, true);
        filesCreator.generateFile("src/test/resources/long_file/drivers.json");
    }
}
