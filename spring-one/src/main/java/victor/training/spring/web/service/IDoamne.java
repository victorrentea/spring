package victor.training.spring.web.service;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

public interface IDoamne {
   String method();
}
@Service
//@Profile("!acasa")
class DoamneBun implements IDoamne {
   @Override
   public String method() {
      return "ajuta";
   }
}
@Service
@Primary
@Profile("acasa")
class DoamneDummy implements IDoamne {
   public String method() {
      return "vechiul testament";
   }
}
