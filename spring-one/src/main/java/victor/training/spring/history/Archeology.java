package victor.training.spring.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootApplication
public class Archeology implements CommandLineRunner {
   @Autowired
   Test1 test1;

   public static void main(String[] args) {
      SpringApplication.run(Archeology.class, args);
   }

   @Override
   public void run(String... args) throws Exception {
//      test1.methodToTest();
   }
}

@Service
class Test1 { // default name of "test1"

   private final Other other;
   private final Other other2;

   public Test1(Other other, Other other2) {
      this.other = other;
      this.other2 = other2;
   }

   public int methodToTest() {

      int r = other.met();

      return r + 1;//pretend heavy logic based on the return value of other.met()
   }
}
@Component
class Other {
   @Autowired
   public A a;

//   @Autowired
//   public void setA(A a) {
//      this.a = a;
//   }

   public Other() {
      //load some config
      ThreadUtils.sleep(1000);

   }

   public int met() {
      return 1;
   }
}

//@Service // heavy logic
//@Component // super-infra stuff. boring stuff (Mappers,)
//@Repository // never use-- instead exend from MongoRepository
//@Controller // jsp/velocity --- server-side generated HTML - never today
//@Configuration //
//@RestController // REST

@Retention(RetentionPolicy.RUNTIME)
@Service
@interface Facade {

}

@Facade
class A {
}