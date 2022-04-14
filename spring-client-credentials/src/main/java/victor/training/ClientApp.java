package victor.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@EnableScheduling
@SpringBootApplication
public class ClientApp {
   public static void main(String[] args) {
       SpringApplication.run(ClientApp.class, args);
   }

   @Scheduled(fixedDelay = 3000)
   public void send() throws SAXException, ParserConfigurationException {


//      SAXParserFactory spf = SAXParserFactory.newInstance();
//      spf.setFeature("https://apache.org/xml/features/disallow-doctype-decl", true);
//      SAXParser saxParser = spf.newSAXParser();
   }
}
