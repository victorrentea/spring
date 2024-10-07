package victor.training.spring.first;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties(Props.class)
// fill the fields of this class with the values from application.properties/.yml
// and make it available for injection (add it as a bean in the context)

// by default spring loads application.properties/.yml
// from /src/main/resources or /src/main/resources/config
@Import({
    X.class,
    X2.class,
    YMe.class,
    Y2.class,
    MailServiceImpl.class
})
public class MyModuleConfig {
}

//@Configuration // without

