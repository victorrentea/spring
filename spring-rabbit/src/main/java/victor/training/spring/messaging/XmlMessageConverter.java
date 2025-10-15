package victor.training.spring.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import victor.training.spring.messages.Invoice;
import victor.training.spring.messages.Notification;
import victor.training.spring.messages.Order;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlMessageConverter implements MessageConverter {
  private static final Logger logger = LoggerFactory.getLogger(XmlMessageConverter.class);

  private static final Pattern ROOT_TAG = Pattern.compile("<\\s*([\\w:-]+)");
  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  @Override
  public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
    try {
      JAXBContext ctx = JAXBContext.newInstance(object.getClass());
      Marshaller marshaller = ctx.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      marshaller.marshal(object, os);
      byte[] body = os.toByteArray();

      if (messageProperties != null) {
        messageProperties.setContentType("text/xml");
        messageProperties.setContentEncoding(DEFAULT_CHARSET.name());
        messageProperties.setContentLength(body.length);
      }
      return new Message(body, messageProperties == null ? new MessageProperties() : messageProperties);
    } catch (Exception e) {
      throw new MessageConversionException("Failed to marshal object to XML", e);
    }
  }

  @Override
  public Object fromMessage(Message message) throws MessageConversionException {
    if (message == null) return null;
    byte[] body = message.getBody();
    if (body == null || body.length == 0) return null;

    String xml;
    try {
      String encoding = message.getMessageProperties() != null ? message.getMessageProperties().getContentEncoding() : null;
      Charset cs = encoding != null ? Charset.forName(encoding) : DEFAULT_CHARSET;
      xml = new String(body, cs);
    } catch (Exception e) {
      throw new MessageConversionException("Failed to decode message body", e);
    }

    String root = detectRoot(xml);
    if (root == null) {
      logger.warn("Could not detect root element for incoming XML, returning raw String");
      return xml;
    }

    try {
      switch (root) {
        case "Order":
          return unmarshal(xml, Order.class);
        case "Invoice":
          return unmarshal(xml, Invoice.class);
        case "Notification":
          return unmarshal(xml, Notification.class);
        default:
          logger.warn("Unknown root element '{}', returning raw String", root);
          return xml;
      }
    } catch (JAXBException je) {
      throw new MessageConversionException("Failed to unmarshal XML to object", je);
    }
  }

  private String detectRoot(String xml) {
    if (xml == null) return null;
    Matcher m = ROOT_TAG.matcher(xml);
    if (m.find()) {
      String tag = m.group(1);
      int colon = tag.indexOf(':');
      if (colon >= 0) {
        return tag.substring(colon + 1);
      }
      return tag;
    }
    return null;
  }

  private <T> T unmarshal(String xml, Class<T> clazz) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(clazz);
    Unmarshaller unmarshaller = context.createUnmarshaller();

    try {
      // secure SAX parsing
      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setNamespaceAware(true);
      try { spf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); } catch (Exception ignored) {}
      try { spf.setFeature("http://xml.org/sax/features/external-general-entities", false); } catch (Exception ignored) {}
      try { spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false); } catch (Exception ignored) {}

      XMLReader xr = spf.newSAXParser().getXMLReader();
      InputSource is = new InputSource(new StringReader(xml));
      SAXSource source = new SAXSource(xr, is);

      @SuppressWarnings("unchecked")
      T result = (T) unmarshaller.unmarshal(source);
      return result;
    } catch (JAXBException je) {
      throw je;
    } catch (Exception e) {
      throw new JAXBException("Failed to parse XML securely", e);
    }
  }
}
