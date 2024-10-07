package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

public class Y2WithProps {
  private final Props props;

  public Y2WithProps(Props props) {
    this.props = props;
  }
  // no @Value("${}") anymore anywhere

  @EventListener(ApplicationStartedEvent.class)
  public void atStart() {
    System.out.println("Y2 with props: " + props.gate());
  }
}
