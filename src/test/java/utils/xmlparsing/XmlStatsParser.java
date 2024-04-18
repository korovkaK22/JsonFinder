package utils.xmlparsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlStatsParser {


    /**
     * Parses an XML file and extracts statistics into a map.
     *
     * @param xmlFilePath The path to the XML file to parse.
     * @return A map where keys are the 'value' tags and values are the 'count' tags as integers.
     */
    public static Map<String, Integer> parseXmlToMap(String xmlFilePath) {
        Map<String, Integer> statistics = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(xmlFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                addNodeToStatistic(nodeList.item(i), statistics);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return statistics;
    }

    /**
     * Process Node and add it into statistic
     * @param node node that must be processed
     * @param statistics statistic
     */
    private static void addNodeToStatistic(Node node, Map<String, Integer> statistics) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String value = element.getElementsByTagName("value").item(0).getTextContent();
            int count = Integer.parseInt(element.getElementsByTagName("count").item(0).getTextContent());
            statistics.put(value, count);
        }
    }
}