package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  private final MailService mailService; // polymorphic injection
//  @Value("${props.gate}")
//  private Integer gate;
//  @Value("${props.welcomeMessage:Hi!}")
//  private String message;
  private final Props props;

  public Y(MailService mailService, Props props) {
    this.mailService = mailService;
    this.props = props;
  }

  public int logic() {
    mailService.sendEmail("Go to gate " + props.gate());

    return 1;
  }
}
