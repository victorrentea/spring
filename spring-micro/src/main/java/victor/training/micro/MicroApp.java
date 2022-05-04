package victor.training.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MicroApp {
   public static void main(String[] args) {
       SpringApplication.run(MicroApp.class, args);
   }

   @GetMapping
   public String get() {
      return "Micro received " + SecurityContextHolder.getContext().getAuthentication().getName();
   }

   @GetMapping("/api/teacher/{teacherId}/bio")
   public String getTeacherBio(@PathVariable Long teacherId) throws InterruptedException {
      Thread.sleep(300);
      return "Amazing bio for teacher " + teacherId + " retrieved from remote service";
   }
}
