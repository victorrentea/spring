package victor.training.spring;

import org.junit.jupiter.api.Test;
import victor.training.spring.messages.XmlMessage;
import victor.training.spring.messages.Order;
import victor.training.spring.messages.Invoice;
import victor.training.spring.messages.Notification;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class XmlMessageSealedTest {

  @Test
  public void orderIsXmlMessage() throws Exception {
    String xml = "<Order><Id>ORD-1</Id><Customer>X</Customer><Items></Items><Total>0</Total></Order>";
    JAXBContext ctx = JAXBContext.newInstance(Order.class);
    Unmarshaller u = ctx.createUnmarshaller();
    Object o = u.unmarshal(new StringReader(xml));
    assertInstanceOf(XmlMessage.class, o);
  }

  @Test
  public void invoiceIsXmlMessage() throws Exception {
    String xml = "<Invoice><Id>INV-1</Id><OrderId>ORD-1</OrderId><Amount>10.0</Amount><DueDate>2025-10-31</DueDate></Invoice>";
    JAXBContext ctx = JAXBContext.newInstance(Invoice.class);
    Unmarshaller u = ctx.createUnmarshaller();
    Object o = u.unmarshal(new StringReader(xml));
    assertInstanceOf(XmlMessage.class, o);
  }

  @Test
  public void notificationIsXmlMessage() throws Exception {
    String xml = "<Notification><Type>INFO</Type><Message>m</Message><Severity>LOW</Severity></Notification>";
    JAXBContext ctx = JAXBContext.newInstance(Notification.class);
    Unmarshaller u = ctx.createUnmarshaller();
    Object o = u.unmarshal(new StringReader(xml));
    assertInstanceOf(XmlMessage.class, o);
  }
}
