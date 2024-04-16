package com.jsonproject.finder.validator;

import com.jsonproject.finder.entity.TaxiDriver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ArgsValidatorImplTest {

    private ArgsValidatorImpl validator;
    @Mock
    private File mockFile;
    private static final List<String> fields  = new ArrayList<>(10);


    @BeforeAll
        static void setupAll(){

        Field[] temp = TaxiDriver.class.getDeclaredFields();
        for (Field field : temp) {
            fields.add(field.getName());
        }
    }


    @BeforeEach
    void setup() {
        validator = new ArgsValidatorImpl();
    }

    @Nested
    class ArgsAmountTest{
        @Test
        void testValidateWithLessThanTwoArguments() {
            String[] args = {"onlyOneArg"};
            assertThrows(IllegalArgumentException.class, () -> validator.validate(args));
        }

        @Test
        void testValidateWithMoreThanTwoArguments() {
            String[] args = {"arg1", "arg2", "arg3"};
            assertThrows(IllegalArgumentException.class, () -> validator.validate(args));
        }

    }

    @Nested
    class DirectoryTest {

        @BeforeEach
        void setup(){
            validator = new ArgsValidatorImpl() {
                private final ArgsValidatorImpl e = new ArgsValidatorImpl();
                @Override
                public void validatePath(File dir){
                    e.validatePath(mockFile);
                }
            };
        }

        @Test
        void testValidateWithNonexistentDirectory() {
            when(mockFile.exists()).thenReturn(false);
            lenient().when(mockFile.isDirectory()).thenReturn(false);
            assertThrows(IllegalArgumentException.class, () -> validator.validate(new String[]{"nonexistent/path", fields.get(0)}));
        }

        @Test
        void testValidateWithPathIsNotADirectory() {
            when(mockFile.exists()).thenReturn(true);
            when(mockFile.isDirectory()).thenReturn(false);
            assertThrows(IllegalArgumentException.class, () -> validator.validate(new String[]{"nonexistent/path", fields.get(0)}));
        }

        @Test
        void testValidateWithPathIsADirectory() {
            when(mockFile.exists()).thenReturn(true);
            when(mockFile.isDirectory()).thenReturn(true);
            assertDoesNotThrow(() -> validator.validate(new String[]{"nonexistent/path", fields.get(0)}));
        }
    }

    @Nested
    class ParametersTest {

        @Test
        void testValidateParameterWithInvalidParameter() {
             assertThrows(IllegalArgumentException.class, () -> validator.validateParameter("invalidParameter"));
        }

        @Test
        void testValidateParameterWithValidParameter() {
            for (String field : fields) {
                assertDoesNotThrow(() -> validator.validateParameter(field));
                assertDoesNotThrow(() -> validator.validateParameter(field.toLowerCase()));
                assertDoesNotThrow(() -> validator.validateParameter(field.toUpperCase()));
            }
        }

    }

}