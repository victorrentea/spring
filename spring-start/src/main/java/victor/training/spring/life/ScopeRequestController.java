package victor.training.spring.life;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScopeRequestController {
    private final RequestMetadata requestMetadata;
    private final AnotherBean anotherBean;

    @GetMapping("scope-request")
    public String requestScope() {
        requestMetadata.setMetadata("Extracted from request: " + "language=EN"); // usually available in all requests
        return anotherBean.method();
    }
}

@Component
class RequestMetadata {
    private String metadata;

    public String getMetadata() {
        return metadata;
    }

    public RequestMetadata setMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }
}

@Service
@RequiredArgsConstructor
class AnotherBean {
    private final RequestMetadata requestMetadata;

    public String method() {
        return "I need the request metadata: " + requestMetadata.getMetadata();
    }
}