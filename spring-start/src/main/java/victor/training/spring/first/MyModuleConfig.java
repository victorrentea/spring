package victor.training.spring.first;

import org.springframework.context.annotation.Import;

//@Configuration // without
//@EnableConfigurationProperties

@Import({
    X.class,
    X2.class,
    Y.class,
    MailServiceImpl.class
})
public class MyModuleConfig {
}
