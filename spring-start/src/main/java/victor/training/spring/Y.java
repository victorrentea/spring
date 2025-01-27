package victor.training.spring;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Y {
//  @Autowired// injection point
//  private MailService mailService; // crapa, ca nu stie pecar
// generat cu lombok
//  public Y(MailService mailService) {
//    this.mailService = mailService;
//  }
  private final MailService mailService;
  @Value("${props.gate}") // apare acum pe param de ctor datorita lombok.config
  private final Integer gate;

  @Value("${o.prop.care.nu.exista}")
  Integer p;


//  public Y(MailService mailService, @Value("${props.gate}") Integer gate) {
//    this.mailService = mailService;
//    this.gate = gate;
//  }

//  @Autowired(required = false)
//  private KafkaAuditor kafkaAuditor;

//  @Autowired
//  @Qualifier("mailServiceDummy") // numele beanului
//  private MailService mailService; // polymorphic injection

//  @Autowired // numele punctului de injectie = camp decide beanul
//  private MailService mailServiceDummy; // nu mai e necesar @Qualifier



  @PostConstruct
  public void laStartup() {
    System.out.println("mailService: " + mailService);
  }


  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}

