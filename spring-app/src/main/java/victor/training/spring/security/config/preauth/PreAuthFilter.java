package victor.training.spring.security.config.preauth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Slf4j
public class PreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    public PreAuthFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(requireNonNull(authenticationManager));
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
        String username = httpRequest.getHeader("x-user");
        String rolesStr = httpRequest.getHeader("x-user-roles");
        if (username == null || rolesStr == null || username.isBlank() || rolesStr.isBlank()) {
            log.error("'x-user' and 'x-user-roles' NOT found in request headers");
            return null;
        }
        List<String> roles = List.of(rolesStr.split(","));

//        roles = UserRole.expandToSubRoles(roles);

        return new PreAuthPrincipal(username, roles);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "null";
    }
}
