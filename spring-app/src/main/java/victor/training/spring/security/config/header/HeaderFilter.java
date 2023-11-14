package victor.training.spring.security.config.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Slf4j
public class HeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
    public HeaderFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(Objects.requireNonNull(authenticationManager));
    }
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
        String username = httpRequest.getHeader("x-user");
        String rolesStr = httpRequest.getHeader("x-user-role");
        if (username == null || rolesStr == null || username.isBlank() || rolesStr.isBlank()) {
            log.error("'x-user' and 'x-user-roles' NOT found in request headers");
            return null;
        }
        List<String> roles = List.of(rolesStr.split(","));
        return new HeaderPrincipal(username, roles);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "null";
    }
}
