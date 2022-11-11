package victor.training.spring.web.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {

    public ApiKeyFilter() {
        setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if (authentication.getPrincipal() instanceof String) {
                    String apiKeyDePeHeader = (String) authentication.getPrincipal();
                    if ("secretKey".equals(apiKeyDePeHeader)) {
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
        return request.getHeader("ApiKey");
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
