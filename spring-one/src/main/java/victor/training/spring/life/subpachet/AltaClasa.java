package victor.training.spring.life.subpachet;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Component
@Facade
//@Service
//@Repository
//@Controller
//@RestController
//@Configuration
public class AltaClasa {
}

@Retention(RetentionPolicy.RUNTIME)
@Component
@interface Facade {

}
