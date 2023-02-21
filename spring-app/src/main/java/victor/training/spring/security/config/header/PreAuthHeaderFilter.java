package victor.training.spring.security.config.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
public class PreAuthHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
    public PreAuthHeaderFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String username = request.getHeader("x-user");
        String rolesStr = request.getHeader("x-user-role");
        if (username == null || rolesStr == null || username.isBlank() || rolesStr.isBlank()) {
            log.error("'x-user' and 'x-user-roles' NOT found in request headers");
            return null;
        }
        List<String> roles = List.of(rolesStr.split(","));
        return new PreAuthHeaderPrincipal(username, roles);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "null";
    }
}
