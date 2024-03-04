package victor.training.spring.first;

public class Y {
  private final MailService mailService; // polymorphic injection
  private final Props props;

  public Y(MailService mailService, Props props) {
    this.mailService = mailService;
    this.props = props;
  }

  public int logic() {
    mailService.sendEmail(
        "Go to gate " + props.gate());

    return 1;
  }
}
