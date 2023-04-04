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
        String username = request.getHeader("x-username");
        String sessionId = request.getHeader("x-session-id");
        if (username == null || sessionId == null || username.isBlank() || sessionId.isBlank()) {
            log.error("'x-username' and 'x-session-id' NOT found in request headers");
            return null;
        }
        return new PreAuthHeaderPrincipal(username, sessionId, List.of());
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "null";
    }
}
