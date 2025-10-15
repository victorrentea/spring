package victor.training.spring.messages;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "Notification")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Notification implements XmlMessage {

  @XmlElement(name = "Type")
  private String type;

  @XmlElement(name = "Message")
  private String message;

  @XmlElement(name = "Severity")
  private String severity;

}
