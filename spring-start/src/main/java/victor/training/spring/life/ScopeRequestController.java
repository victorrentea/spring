package victor.training.spring.life;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScopeRequestController {
    private final RequestScoped requestScoped;
    private final AnotherBean anotherBean;
    @GetMapping("scope-request")
    public String requestScope() {
        requestScoped.setMetadata("Extracted from request"); //  ideally available in all requests
        return anotherBean.method();
    }
}

@Data
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestScoped {
    private String metadata;
}

@Service
@RequiredArgsConstructor
class AnotherBean {
    private final RequestScoped requestScoped;
    public String method() {
        return "Obtained magically: " + requestScoped.getMetadata();
    }
}