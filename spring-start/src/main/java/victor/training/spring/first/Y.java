package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  private final MailService mailServiceImpl; // polymorphic injection
  private final Props props;

//  private final String env;

//  public Y(MailService mailService) { // fails 2 found. unless @Primary
//  public Y(MailServiceImpl mailService) {
//  public Y(@Qualifier("impl") MailService mailService) {
  public Y(MailService mailService,
//           @Value("${props.env:default_DONTDOTHIS}") String env) {
           Props props) {
    this.mailServiceImpl = mailService;
    this.props = props;
  }

  public int logic() {
    mailServiceImpl.sendEmail("Deployed in " + props.env());

    return 1;
  }
}
