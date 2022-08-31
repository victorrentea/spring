package victor.training.spring.life;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class LifeApp implements CommandLineRunner {
    @Bean
    public static CustomScopeConfigurer defineThreadScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        // WARNING: Can leaks memory if data remains on thread. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html
        // TODO finally { ClearableThreadScope.clearAllThreadData(); }
        configurer.addScope("thread", new ClearableThreadScope());
        return configurer;
    }

    public static void main(String[] args) {
        SpringApplication.run(LifeApp.class);
    }

    @Autowired
    private OrderExporter exporter;

    // TODO [1] make singleton; multi-thread + mutable state = BAD
    // TODO [2] instantiate manually, set dependencies, pass around; no AOP
    // TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
    // TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc

    public void run(String... args) {
        new Thread(() -> exporter.export(Locale.ENGLISH)).start();
        new Thread(() -> exporter.export(Locale.FRENCH)).start();
    }
}

@RequiredArgsConstructor
@Slf4j
@Service
class OrderExporter {
    private final LabelService labelService;
    private final LabelService labelService2;

    public void export(Locale locale) {
        log.info("Running export in " + locale);
        log.info("Origin Country: " + labelService.getCountryName(locale, "rO"));
    }
}


//@Scope("prototype")// scope: singleton, request, session, prototype.
@RequiredArgsConstructor
@Slf4j
@Service
class LabelService {
    private final CountryRepo countryRepo;

    private final Map<Locale, Map<String, String>> countryNames = new HashMap<>(); // shared mutable data in a singleton, + multithread = RIP

    @PostConstruct
    public void load() {
        log.info(this + ".load()");
        for (Locale locale : List.of(Locale.ENGLISH, Locale.FRENCH)) {
            countryNames.put(locale, countryRepo.loadCountryNamesAsMap(locale));
        }
    }

    public String getCountryName(Locale locale, String iso2Code) {
        log.info(this + ".getCountryName()");
        return countryNames.get(locale).get(iso2Code.toUpperCase());
    }

    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}


@RequiredArgsConstructor
@RestController
class MyMultitenantController {
    private final MyService service;

    @GetMapping
    public void exec() throws InterruptedException {
        service.method();
    }
}

@Slf4j
@RequiredArgsConstructor
@Service
class MyService {
    private final RequestContext context;
    public void method() throws InterruptedException {
        String tenantId = context.getTenantId();
        Thread.sleep(10000);
        System.out.println("Tenant: " + tenantId);
    }
}

@Component
@Data
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestContext {
    private String tenantId;
}

@RequiredArgsConstructor
@Component
class MyFilter implements Filter {
    private final RequestContext context;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String tenantId = ((HttpServletRequest) request).getHeader("Tenant-Id");
        System.out.println("tentantId in filter: " + tenantId);
        context.setTenantId(tenantId);

        chain.doFilter(request,response);
    }
}