package victor.training.micro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@EnableAsync
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
      try {
         microApp.someOther();
         return "Amazing bio for teacher id=" + teacherId + " retrieved from remote API as username=" + username;
      } finally {
         threadLocal.remove();
      }
   }

   @Autowired
   @Lazy
   private MicroApp microApp;

   @Async
   public void someOther() throws InterruptedException {
      String user = threadLocal.get();
      Thread.sleep(2000);
      log.info("Again user " + user); // the log line has at its start the traceId of the original request
      // proving that the Sleuth request metadata was magically propagated over an @Async call
   }
   public static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
}
@Configuration
class MyConfig {
   @Bean
   public ThreadPoolTaskExecutor executor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(7);
      executor.setMaxPoolSize(7);
      executor.setThreadNamePrefix("ex-");
      executor.setQueueCapacity(200);
      executor.setTaskDecorator(new TaskDecorator() {
         @Override
         public Runnable decorate(Runnable runnable) {
            // In tomcat's tread
            String user = MicroApp.threadLocal.get();
            return () -> {
               // in worker thread
               MicroApp.threadLocal.set(user);
               try {
                  runnable.run();
               } finally {
                  MicroApp.threadLocal.remove(); // MUST-HAVE
               }
            };
         }
      });
      return executor;
   }
}