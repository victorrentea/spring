package victor.training.spring.first.davide;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@Profile("catchasync")
public class AsyncExceptions implements AsyncConfigurer {
   private static Throwable lastException; // TODO use ThreadLocals if you want to run in parallel


   public static Throwable pullLastAsyncException() {
      Throwable exception = AsyncExceptions.lastException;
      lastException  = null;
      return exception;
   }

   @Override
   public Executor getAsyncExecutor() {
      System.out.println("CONFIGURE");
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(5);
      executor.setMaxPoolSize(5);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("test-");
      executor.setTaskDecorator(runnable -> {
         return () -> {
            System.out.println("START");
            runnable.run();
            System.out.println("DONE");
         };
      });
      executor.initialize();
      return executor;
   }

   @Override
   public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
      return (ex, method, params) -> {
         System.err.println(" CAUGHT " + ex + " in " + method);
         lastException =ex;
      };
   }
}
