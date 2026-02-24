package victor.training.spring.first;

import org.springframework.context.annotation.Import;

@Import({
    X.class,
    Y.class,
    Z.class,
    MailServiceDummy.class
})
public class FirstConfig {
}
