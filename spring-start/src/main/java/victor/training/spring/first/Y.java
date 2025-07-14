package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Service
@Retention(RetentionPolicy.RUNTIME)
@interface DomainService {
}

@Slf4j
@DomainService
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
    mailService.sendEmail("Go to gate " + props.gate());
//    props.setGate(-1);
    return 1;
  }

  @EventListener
//  @Async // dar multe  mai vei avea probleme acum
  public void message(MyEvent event){
    log.info(event.data());
    // SHOCK: ruleaza in threadul si tranzactia publisherului
  }
}
