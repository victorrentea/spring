package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Y {
  private final MailService mailService;
  @Value("${props.gate}")
  private Integer gate;

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
