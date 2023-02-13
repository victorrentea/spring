package victor.training.spring.web.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScopeRequestController {
  private final RequestScoped requestScoped;
  private final AnotherBean anotherBean;

  @GetMapping("api/scope-request")
  public String requestScope(@RequestParam(defaultValue = "ro") String lang) {
    log.info("Got metadata on request: " + lang);
    requestScoped.setMetadata(lang);
    return anotherBean.method();
  }
}

@Data
@Component
// experiment system behavior without the next line:
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestScoped {
  private String metadata;
}

@Slf4j
@Service
@RequiredArgsConstructor
class AnotherBean {
  private final RequestScoped requestScoped;

  public String method() {
    String metadata = requestScoped.getMetadata();
    log.info("Got metadata deep in code: " + metadata);
    return "Obtained magically: " + metadata;
  }
}