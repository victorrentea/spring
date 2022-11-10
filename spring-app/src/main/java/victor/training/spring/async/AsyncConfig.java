package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
@Slf4j
public class AsyncConfig {

	@Bean
	public ThreadPoolTaskExecutor bar(@Value("${bar.thread.count}") int barThreadCount) {
		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(barThreadCount);
		threadPool.setMaxPoolSize(barThreadCount);
		threadPool.setQueueCapacity(10); //ca-i covid
		threadPool.setThreadNamePrefix("bar-");

		//  nu poti asa prinde ex din taskurile submise
//		threadPool.setTaskDecorator(new TaskDecorator() {
//			@Override
//			public Runnable decorate(Runnable originalTaskSumitted) {
//				return () -> {
//					try {
//						log.info("INAINTE");
//						originalTaskSumitted.run();
//						log.info("DUPA");
//					} catch (Exception e) {
//						log.error("uite-o, ex din worked thread", e);
//					}
//				};
//			}
//		});
		threadPool.initialize();
		return threadPool;
	}
}



