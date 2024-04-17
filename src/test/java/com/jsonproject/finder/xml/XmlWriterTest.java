package com.jsonproject.finder.xml;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@ExtendWith(MockitoExtension.class)
class XmlWriterTest {
    private XmlWriter xmlWriter;

    @BeforeEach
    void setUp() throws JAXBException {
        XmlStatistic xmlStatistic = mock(XmlStatistic.class);
        xmlWriter = new XmlWriter(xmlStatistic);
    }

    @Test
    void writeXmlStatsToNullFile() {
        assertThrows(NullPointerException.class, () -> xmlWriter.writeXmlStatsToXml(null));
    }

    @Test
    void writeXmlStatsToFileWithNullStatistic() {
        assertThrows(NullPointerException.class, () -> new XmlWriter(null));
    }

    @Test
    void writeXmlStatsToNoAccessFile(@TempDir File tempDir) throws IOException {
        File file = new File(tempDir, "lockedFile.xml");
        file.createNewFile();
        file.setReadOnly();
        assertThrows(JAXBException.class, () -> xmlWriter.writeXmlStatsToXml(file));
    }

    @Test
    void writeSuccessfullyXmlStatsToFile(@TempDir File tempDir) throws JAXBException {
        File file = new File(tempDir, "test.xml");
        assertTrue(xmlWriter.writeXmlStatsToXml(file));
    }


}