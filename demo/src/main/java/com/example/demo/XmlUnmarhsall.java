package com.example.demo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

public class XmlUnmarhsall {
   public static void main(String[] args) throws JAXBException {
      //language=XML
      String xml= "<person id=\"1\">\n" +
                  "    <name>John</name>\n" +
                  "</person>";

      JAXBContext context = JAXBContext.newInstance("com.example.demo");
      Object ceva = context.createUnmarshaller().unmarshal(new StringReader(xml));

      System.out.println(ceva);
   }
}
