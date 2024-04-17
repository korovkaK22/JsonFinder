package com.jsonproject.finder.xml;

import com.jsonproject.finder.threads.ThreadStatsCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XmlWriter {
    private static final Logger logger = LogManager.getLogger(XmlWriter.class);
    XmlStatistic xmlStatistic;
    Marshaller marshaller;

    public XmlWriter(XmlStatistic xmlStatistic) throws JAXBException {
        this.xmlStatistic = xmlStatistic;
        JAXBContext context = JAXBContext.newInstance(XmlStatistic.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

     public boolean writeXmlStatsToXml(File file) {
        try {
            marshaller.marshal(xmlStatistic, file);
            return true;
        } catch (JAXBException e){
            logger.error(String.format("Can't write xml statistic into %s file", file.getName()),e);
        }
        return false;
    }
}
