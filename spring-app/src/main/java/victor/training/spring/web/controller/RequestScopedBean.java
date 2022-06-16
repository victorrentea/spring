package victor.training.spring.web.controller;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedBean {
       private String tenantId;

       public String getTenantId() {
              return tenantId;
       }

       public void setTenantId(String tenantId) {
              this.tenantId = tenantId;
       }
}
