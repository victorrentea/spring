package victor.training.spring.first;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("x2")
@Scope("prototype")
public class X {
}
