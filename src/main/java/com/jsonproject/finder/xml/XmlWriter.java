package com.jsonproject.finder.xml;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * The XmlWriter class is responsible for writing XML data to a file. It takes an instance of XmlStatistic,
 * which contains the statistics data, and writes this data to an XML file using JAXB for marshalling.
 *
 * @see XmlStatistic
 */
public class XmlWriter {
    XmlStatistic xmlStatistic;
    Marshaller marshaller;

    /**
     * Constructs an XmlWriter with the specified XmlStatistic object.
     * Initializes JAXB context and marshaller for the XmlStatistic class.
     *
     * @param xmlStatistic the XmlStatistic object containing the data to be written to XML.
     * @throws JAXBException if an error occurs during the creation of JAXBContext or Marshaller.
     * @throws NullPointerException if xmlStatistic is null.
     */
    public XmlWriter(XmlStatistic xmlStatistic) throws JAXBException, NullPointerException {
        if (xmlStatistic == null) {
            throw new NullPointerException("Statistic can't be null");
        }

        this.xmlStatistic = xmlStatistic;
        JAXBContext context = JAXBContext.newInstance(XmlStatistic.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }


    /**
     * Writes the XML representation of the XmlStatistic object to the specified file.
     *
     * @param file the file to which the XML data will be written.
     * @return true if the operation is successful.
     * @throws JAXBException if an error occurs during the marshalling process.
     * @throws NullPointerException if the file is null.
     */
    public boolean writeXmlStatsToXml(File file) throws JAXBException {
        if (file == null) {
            throw new NullPointerException("Can't write statistic to null file");
        }

        marshaller.marshal(xmlStatistic, file);
        return true;
    }
}
