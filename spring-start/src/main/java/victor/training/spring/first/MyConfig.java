package victor.training.spring.first;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    X.class,
    Y.class,
    MailServiceDummy.class})
public class MyConfig {

}
