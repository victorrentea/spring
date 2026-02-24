package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  private final MailService mailService; // polymorphic injection
  private final Integer gate;
  // hard-coded @ injection point
//  public Y(@Qualifier("victor.training.spring.first.MailServiceImpl") MailService mailService) {
//  public Y(MailServiceImpl mailService) { // quite similar to the above

  public Y(MailService mailService,
           @Value("${props.gate:890}") Integer gate) {
    // avoid :defaultValue unless in library/platform code
    this.mailService = mailService;
    this.gate = gate;
  }
  @Autowired
  private Props props;

//  @Autowired
//  public void anyMethod(MailService mailService) {
//    this.mailService = mailService; // don't use this at all!
//  }

  public int logic() {
    mailService.sendEmail("Go to gate " + props.gate());

    return 1;
  }
}

//class MyTest {
//  {
//    var y = new Y();
//    MailServiceDummy dep = new MailServiceDummy();
//     inejct using reflection
//    Class<Y> yClass = Y.class;
//    yClass.getDeclaredField().set
//  }
//}
