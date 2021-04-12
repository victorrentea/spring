//package victor.training.spring.web.metrics.cache;
//
//import io.micrometer.core.instrument.Tag;
//import io.micrometer.core.instrument.binder.MeterBinder;
//import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ConcurrentMapCacheBinderProvider implements CacheMeterBinderProvider<ConcurrentMapCacheMetricsWrapper> {
//
//    @Override
//    public MeterBinder getMeterBinder(ConcurrentMapCacheMetricsWrapper cache, Iterable<Tag> tags) {
//        return new ConcurrentMapCacheMeterBinder(cache, tags);
//    }
//}