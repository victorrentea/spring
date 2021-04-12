//package victor.training.spring.web.metrics.cache;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.support.SimpleCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//
//@Configuration
//public class CacheMetricsConfig extends CachingConfigurerSupport {
//   @Value("${spring.cache.cache-names}")
//   private List<String> cacheNames;
//
//   @Bean
//   @Override
//   public CacheManager cacheManager() {
//      SimpleCacheManager cacheManager = new SimpleCacheManager();
//      cacheManager.setCaches(cacheNames.stream().map(ConcurrentMapCacheMetricsWrapper::new).collect(toList()));
//      return cacheManager;
//   }
//}
