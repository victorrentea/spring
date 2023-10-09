package victor.training.spring.first.subp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;
import victor.training.spring.first.X;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class Y {
  private final MailService mailService;
  private final String message = "HALO";

  @Autowired
  @Lazy // spring pune un proxy pana apuca sa-l initializeze pe X
  private X x;

  @PostConstruct
  public void atStartup() {
    System.out.println("Cine e x: " + x.getClass());
  }
  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);
    return 1;
  }
}
