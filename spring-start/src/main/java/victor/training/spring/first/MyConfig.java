package victor.training.spring.first;

import org.springframework.context.annotation.Import;

//@Configuration //no application logic, only Spring glue, beans, annotation ,.. etc
@Import({
    X.class,
    Y.class,
})
public class MyConfig {// implements somth from Spring
}
