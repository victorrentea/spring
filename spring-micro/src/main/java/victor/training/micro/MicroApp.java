package victor.training.micro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class MicroApp {
   public static void main(String[] args) {
       SpringApplication.run(MicroApp.class, args);
   }

   @GetMapping
   public String get() {
      return "Micro received username= '" + SecurityContextHolder.getContext().getAuthentication().getName() + "'";
   }

   @GetMapping("/api/teachers/{teacherId}/bio")
   public String getTeacherBio(@PathVariable Long teacherId) throws InterruptedException {
      Thread.sleep(3000);
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      log.info("Serving user {}", username);
      threadLocal.set(username);
      someOther();
      return "Amazing bio for teacher id=" + teacherId + " retrieved from remote API as username=" + username;
   }

   public void someOther() {
      String user = threadLocal.get();
      log.info("Again user " + user);
   }

   private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
//   private static String currentSessionId;
}
