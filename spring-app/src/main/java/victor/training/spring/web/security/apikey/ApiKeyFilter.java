package victor.training.spring.web.security.apikey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {

    public ApiKeyFilter() {
        setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if (authentication.getPrincipal() instanceof String) {
                    String apiKeyFromHeader = (String) authentication.getPrincipal();
                    if ("secret".equals(apiKeyFromHeader)) {
                        authentication.setAuthenticated(true);
                        return authentication;
                    }
                }
                throw new BadCredentialsException("Incorrect api key");
            }
        });
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader("X-Api-Key");
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
