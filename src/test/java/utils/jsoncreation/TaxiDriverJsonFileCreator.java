package utils.jsoncreation;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsonproject.finder.entity.TaxiDriver;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;


public class TaxiDriverJsonFileCreator {
    private final Logger logger = LogManager.getLogger();
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonFactory jsonFactory = new JsonFactory();

    {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public TaxiDriverJsonFileCreator() {

    }


    public void generateFile(String path, List<TaxiDriver> drivers) {
        try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new File(path), JsonEncoding.UTF8)) {

            jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
            jsonGenerator.writeStartArray();


            for (TaxiDriver driver : drivers) {
                mapper.writeValue(jsonGenerator, driver);
            }

            jsonGenerator.writeEndArray();

        } catch (IOException e) {
            logger.error(String.format("Can't create file \"%s\"", path), e);
        }
    }

    public void generateFile(String path, TaxiDriverCreator creator, int amount) {
        try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new File(path), JsonEncoding.UTF8)) {

            jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
            jsonGenerator.writeStartArray();

            for (int i = 0; i < amount; i++) {
                TaxiDriver driver = creator.getTaxiDriver();
                mapper.writeValue(jsonGenerator, driver);
            }

            jsonGenerator.writeEndArray();
        } catch (IOException e) {
            logger.error(String.format("Can't create file \"%s\"", path), e);
        }
    }


    /**
     * get all fields' names
     *
     * @param clazz class which has fields
     * @return array with fields' names
     */
    public static List<String> getFieldNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>(10);
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

}
