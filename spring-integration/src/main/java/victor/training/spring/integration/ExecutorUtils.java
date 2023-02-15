package victor.training.spring.integration;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ExecutorUtils {
  public static ThreadPoolTaskExecutor executor(String name, int threads, int queueCapacity) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix(name + "-");
    executor.setCorePoolSize(threads);
    executor.setMaxPoolSize(threads);
    executor.setQueueCapacity(queueCapacity);
    executor.initialize();
    return executor;
  }
}
