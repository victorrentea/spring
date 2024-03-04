package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public class Y {
  private final MailService mailService; // polymorphic injection
  private final Props props;

  public Y(MailService mailService, Props props) {
    this.mailService = mailService;
    this.props = props;
  }

  public int logic() {
    mailService.sendEmail(
        "Go to gate " + props.getGate());

    return 1;
  }
}
