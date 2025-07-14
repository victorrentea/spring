package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Y { // numele "y"
//  public Y(MailService mailService,
//           List<MailService> toateImplementarile,
//           @Value("${props.gate}")Integer gate) {
//    this.mailService = mailService;
//    this.toateImplementarile = toateImplementarile;
//    this.gate = gate;
//  }
  private final MailService mailService; // polymorphic injection
  private final List<MailService> toateImplementarile;
  private final Props props;
//  @Value("${props.gate:667}") // copiat pe param de ctor prin lombok.config
//  private final Integer gate;


  @PostConstruct // Springule, ruleaz-o dupa ce ai injectat toate dep
  public void init() {
    System.out.println("EU:"+toateImplementarile);
  }

  public int logic() {
    mailService.sendEmail("Go to gate " + props.getGate());

    return 1;
  }
}
