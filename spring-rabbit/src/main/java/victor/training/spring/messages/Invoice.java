package victor.training.spring.messages;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "Invoice")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Invoice implements XmlMessage {

  @XmlElement(name = "Id")
  private String id;

  @XmlElement(name = "OrderId")
  private String orderId;

  @XmlElement(name = "Amount")
  private double amount;

  @XmlElement(name = "DueDate")
  private String dueDate; // ISO date string for simplicity

}
