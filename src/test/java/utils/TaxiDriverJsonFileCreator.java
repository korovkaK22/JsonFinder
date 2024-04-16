package utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import com.jsonproject.finder.entity.TaxiDriver;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class TaxiDriverJsonFileCreator {
    private final Logger logger = LogManager.getLogger();
    private final int entityAmount;
    private final TaxiDriverCreator creator;
    private final boolean hasMistakes;
    private final Random random = new Random(22);


    public void generateFile(String path) {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();
        try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new File(path), JsonEncoding.UTF8)) {

            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

            jsonGenerator.writeStartArray();

            List<String> fieldNames = getFieldNames(TaxiDriver.class);

            for (int i = 0; i < entityAmount; i++) {
                TaxiDriver driver = creator.getTaxiDriver();
                //Add mistake factor
                if (hasMistakes && random.nextBoolean()) {
                    String dutyParameterName = fieldNames.get(random.nextInt(1, fieldNames.size()));
                    driver.setName(String.format("Invalid record without %s" , dutyParameterName));
                    ObjectNode driverNode = mapper.valueToTree(driver);
                    driverNode.remove(dutyParameterName);
                    mapper.writeTree(jsonGenerator, driverNode);
                } else {
                    mapper.writeValue(jsonGenerator, driver);
                }
            }

            jsonGenerator.writeEndArray();

        } catch (IOException e) {
            logger.error(String.format("Can't create file \"%s\"", path), e);
        }
    }

    /**
     * get all fields' names
     * @param clazz class which has fields
     * @return array with fields' names
     */
    public static List<String> getFieldNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

}
