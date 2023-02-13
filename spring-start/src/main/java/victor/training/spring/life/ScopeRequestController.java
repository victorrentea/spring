package victor.training.spring.life;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// what kind of component can intercept all http request/response and do stuff on them
@RequiredArgsConstructor
@Component
class MyFilter implements Filter {
    private final RequestMetadata requestMetadata;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) request;
        String httpRequestLocale = r.getLocale().toString();
//        requestMetadata.setMetadata(httpRequestLocale);
        chain.doFilter(request, response);
    }
}

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScopeRequestController {
    private final RequestMetadata requestMetadata;
    private final AnotherBean anotherBean;

    @GetMapping("scope-request") //t?lang=EN
    public String requestScope(@RequestParam String lang) throws ExecutionException, InterruptedException {
        requestMetadata.setMetadata(lang); // usually available in all requests
        log.info("Start in controller");
        String result = CompletableFuture.supplyAsync(()->anotherBean.method()).get();
        return result;
    }
}

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // DOES NOT PROPAGATE AUTOMATICALLY OVER NEW THREADS
class RequestMetadata {
    private String metadata; // mutable!!
    public String getMetadata() {
        return metadata;
    }
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
@Slf4j
@Service
@RequiredArgsConstructor
class AnotherBean {
    private final RequestMetadata requestMetadata;
    @SneakyThrows
    public String method() {
        log.info("Who am i talking to ?! " + requestMetadata.getClass() );
//        if(true) throw new RuntimeException("Intetional");
        Thread.sleep(2000);
        return "I need the request metadata: USER_LANG=" + requestMetadata.getMetadata();
        // crash because on the thread this line runs now there is no ACTIVE HTTP REQUEST anymore, as you are not
        // running in the Tomcat thread anymomre
    }
}