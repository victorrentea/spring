package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class Y {
//  @Qualifier("mailServiceImpl")
//  private final MailService mailService; // polymorphic injection

//  private final MailServiceImpl mailService;

  private final MailService mailService;

  private final List<MailService> mailServiceToate;

  public int logic() {
    System.out.println("TOate de acel tip: " + mailServiceToate);
    mailService.sendEmail("I like 4 topics");
    return 1;
  }
}
