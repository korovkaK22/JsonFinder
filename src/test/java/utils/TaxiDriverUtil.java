package utils;

import com.jsonproject.finder.entity.TaxiDriver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TaxiDriverUtil {

    public static List<String> getTaxiDriverFields() {
        List<String> fields = new ArrayList<>(10);
        Field[] temp = TaxiDriver.class.getDeclaredFields();
        for (Field field : temp) {
            fields.add(field.getName());
        }
        return fields;
    }

    public static String getFieldValue(TaxiDriver driver, String fieldName)
            throws IllegalAccessException, NoSuchFieldException{
        Field field = TaxiDriver.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(driver);
        return value != null ? value.toString() : "";
    }


}
