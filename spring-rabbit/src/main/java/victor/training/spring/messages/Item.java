package victor.training.spring.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  @XmlElement(name = "SKU")
  private String sku;

  @XmlElement(name = "Quantity")
  private int quantity;

  @XmlElement(name = "Price")
  private double price;

  // Lombok generates constructors, getters, setters, toString, equals, hashCode
}
