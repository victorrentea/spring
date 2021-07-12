package victor.training.spring.first.davide;

import com.jayway.awaitility.Awaitility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootApplication
@EnableAsync
class DavideApp {

}

@Service
public class DavideCase {
   private final Other other;

   public DavideCase(Other other) {
      this.other = other;
   }

   public void method() {
      other.asyncMethod();
   }
}

@Component
class Other {

   @Async
   public void asyncMethod() {
      throw new RuntimeException("To capture");
   }
}

@SpringBootTest
@ActiveProfiles("catchasync")
class DavideCaseTest {
   @Autowired
   DavideCase davideCase;

   @Test
   public void method() {
      davideCase.method();
      Throwable exception = Awaitility.await().atMost(1, SECONDS)
          .until(AsyncExceptions::pullLastAsyncException, is(notNullValue()));

      Assertions.assertThat(exception).hasMessageContaining("captur");
   }


}

