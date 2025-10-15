package victor.training.spring.messages;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Order implements XmlMessage {

  @XmlElement(name = "Id")
  private String id;

  @XmlElement(name = "Customer")
  private String customer;

  @XmlElementWrapper(name = "Items")
  @XmlElement(name = "Item")
  private List<Item> items = new ArrayList<>();

  @XmlElement(name = "Total")
  private double total;
}
