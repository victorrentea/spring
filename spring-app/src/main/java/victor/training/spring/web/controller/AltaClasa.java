package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AltaClasa {
    @Autowired
    private RequestScopedBean requestScopedBean;
//@Async
    public void pesteMariSiLayereDiastanta() {
//        KeycloakPrincipal<KeycloakSecurityContext> principal =
//                (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        principal.getKeycloakSecurityContext().getIdToken().get

        System.out.println("Oare ce mi-o fi injectat springu " + requestScopedBean.getClass());

        String tenantId = requestScopedBean.getTenantId();
        log.debug("tenantu la final ex " + tenantId);
    }
}
