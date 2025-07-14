package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Y { // numele "y"
  @Autowired
  private MailService mailService; // polymorphic injection
  @Autowired
  private List<MailService> toateImplementarile;
  @Value("${props.gate}")
  private Integer gate;

  @PostConstruct // Springule, ruleaz-o dupa ce ai injectat toate dep
  public void init() {
    System.out.println("EU:"+toateImplementarile);
  }

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
