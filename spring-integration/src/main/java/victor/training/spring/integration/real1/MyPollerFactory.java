package victor.training.spring.integration.real1;

import org.springframework.integration.dsl.PollerFactory;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class MyPollerFactory {
  private static final Map<String, ThreadPoolTaskExecutor> pollerExecutorForDatasource =
          Collections.synchronizedMap(new HashMap<>());

  public static Function<PollerFactory, PollerSpec> createSingleThreadedPollerForDataSource(String dataSource) {
    return pollerFactory -> pollerFactory
            .fixedRate(1, TimeUnit.SECONDS)
            .taskExecutor(createExecutor(dataSource));
//            .taskExecutor(pollerExecutorForDatasource.computeIfAbsent(dataSource, ds -> createExecutor(ds)));
  }

  private static ThreadPoolTaskExecutor createExecutor(String ds) {
    System.out.println("Create executor for DS: " + ds);
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(1);
    executor.setMaxPoolSize(1);
    executor.setThreadNamePrefix("poll-"+ds+"-");
    executor.initialize();
    return executor;
  }
}
