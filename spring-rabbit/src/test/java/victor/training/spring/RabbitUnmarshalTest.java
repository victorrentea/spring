package victor.training.spring;

import org.junit.jupiter.api.Test;

import victor.training.spring.messages.Order;
import victor.training.spring.messages.Invoice;
import victor.training.spring.messages.Notification;
import victor.training.spring.messages.Item;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RabbitUnmarshalTest {

  @Test
  public void unmarshalOrder() throws Exception {
    String xml = "<Order>" +
        "<Id>ORD-123</Id>" +
        "<Customer>ACME</Customer>" +
        "<Items>" +
        "  <Item><SKU>ABC</SKU><Quantity>2</Quantity><Price>10.5</Price></Item>" +
        "  <Item><SKU>DEF</SKU><Quantity>1</Quantity><Price>5.0</Price></Item>" +
        "</Items>" +
        "<Total>26.0</Total>" +
        "</Order>";

    JAXBContext ctx = JAXBContext.newInstance(Order.class);
    Unmarshaller u = ctx.createUnmarshaller();
    Order order = (Order) u.unmarshal(new StringReader(xml));

    assertNotNull(order);
    assertEquals("ORD-123", order.getId());
    assertEquals("ACME", order.getCustomer());
    List<Item> items = order.getItems();
    assertEquals(2, items.size());
    assertEquals(26.0, order.getTotal());
  }

  @Test
  public void unmarshalInvoice() throws Exception {
    String xml = "<Invoice><Id>INV-1</Id><OrderId>ORD-123</OrderId><Amount>26.0</Amount><DueDate>2025-10-31</DueDate></Invoice>";
    JAXBContext ctx = JAXBContext.newInstance(Invoice.class);
    Unmarshaller u = ctx.createUnmarshaller();
    Invoice invoice = (Invoice) u.unmarshal(new StringReader(xml));

    assertNotNull(invoice);
    assertEquals("INV-1", invoice.getId());
    assertEquals("ORD-123", invoice.getOrderId());
    assertEquals(26.0, invoice.getAmount());
    assertEquals("2025-10-31", invoice.getDueDate());
  }

  @Test
  public void unmarshalNotification() throws Exception {
    String xml = "<Notification><Type>INFO</Type><Message>Order received</Message><Severity>LOW</Severity></Notification>";
    JAXBContext ctx = JAXBContext.newInstance(Notification.class);
    Unmarshaller u = ctx.createUnmarshaller();
    Notification n = (Notification) u.unmarshal(new StringReader(xml));

    assertNotNull(n);
    assertEquals("INFO", n.getType());
    assertEquals("Order received", n.getMessage());
    assertEquals("LOW", n.getSeverity());
  }
}

