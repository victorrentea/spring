package victor.training.spring.transaction.playground;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

@Slf4j
public class InjectP6SpyApplicationListener implements ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        try {
            Class.forName("com.p6spy.engine.spy.P6SpyDriver", false, InjectP6SpyApplicationListener.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            return; // nothing to do
        }

        String oldUrl = applicationContext.getEnvironment().getProperty("spring.datasource.url");
        if (oldUrl == null || oldUrl.startsWith("jdbc:p6spy:")) return; // nothing to do
        String newUrl = "jdbc:p6spy:" + oldUrl.substring("jdbc:".length());
        log.info("Injected \"p6spy:\" in spring.datasource.url:\nold: {}\nnew: {}", oldUrl, newUrl);
        Map<String, Object> changedJdbcProperties = Map.of(
                "spring.datasource.url", newUrl,
                "spring.datasource.driver-class-name", "com.p6spy.engine.spy.P6SpyDriver"
        );
        applicationContext.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("override-jdbc-props", changedJdbcProperties));
    }

}