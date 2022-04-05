package victor.training.spring.events;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncEventsConfig {
   // uncomment below to dispatch ALL events to listeners asynchronously
//	@Bean
//    public ApplicationEventMulticaster applicationEventMulticaster() {
//        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//        return eventMulticaster;
//    }
}
