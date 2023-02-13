package victor.training.spring.life;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScopeRequestController {
    private final RequestMetadata requestMetadata;
    private final AnotherBean anotherBean;

    @GetMapping("scope-request") //t?lang=EN
    public String requestScope(@RequestParam String lang) {
        requestMetadata.setMetadata(lang); // usually available in all requests
        return anotherBean.method();
    }
}
@Component
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestMetadata {
    private String metadata; // mutable!!
    public String getMetadata() {
        return metadata;
    }
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
@Service
@RequiredArgsConstructor
class AnotherBean {
    private final RequestMetadata requestMetadata;

    @SneakyThrows
    public String method() {
        Thread.sleep(2000);
        return "I need the request metadata: USER_LANG=" + requestMetadata.getMetadata();
    }
}