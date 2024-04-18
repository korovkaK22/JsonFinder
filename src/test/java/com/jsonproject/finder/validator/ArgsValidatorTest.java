package com.jsonproject.finder.validator;

import com.jsonproject.finder.utils.ArgsValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.TaxiDriverUtil;

import java.io.File;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ArgsValidatorTest {

    private ArgsValidator validator;
    @Mock
    private File mockFile;
    private static final List<String> fields = TaxiDriverUtil.getTaxiDriverFields();


    @BeforeEach
    void setup() {
        validator = new ArgsValidator();
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
            validator = new ArgsValidator() {
                private final ArgsValidator e = new ArgsValidator();
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