package victor.training.spring.another;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;
import victor.training.spring.first.X;

@Service
public class Y {
  private final X x;
  private final MailService mailService; // polymorphic injection

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(@Lazy X x, MailService mailService) { // @Lazy = allows circular dependencies. AVOID
    this.x = x;
    this.mailService = mailService;
  }
//  @Autowired
//  private X x;
//  @Autowired
//  private MailService mailService; // polymorphic injection

  public int logic() {
    mailService.sendEmail("I like 4 topics");
    return 1;
  }
}
