package victor.training.spring.web;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XXE {
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {

         public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            System.out.println(qName);
         }

         public void characters(char ch[], int start, int length) throws SAXException {
            System.out.println(new String(ch, start, length));
         }
      };

      saxParser.parse(new File("xxe.xml"),handler);
   }
}
