package victor.training.spring.first;

import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Component ???
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface Manager{}
