package victor.training.spring.web;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;

public class XmlBomb {
   public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

      DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = f.newDocumentBuilder();
      Document doc = db.parse(new FileInputStream("bomb.xml"));
      System.out.println(doc);
   }
}
