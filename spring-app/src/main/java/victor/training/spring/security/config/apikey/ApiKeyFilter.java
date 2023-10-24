package victor.training.spring.security.config.apikey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {

    public ApiKeyFilter(String expectedApiKey) {
        setAuthenticationManager(authentication -> {
            if (authentication.getPrincipal() instanceof String apiKeyFromHeader) {
                if (expectedApiKey.equals(apiKeyFromHeader)) {
                    authentication.setAuthenticated(true);
                    return authentication;
                }
            }
            throw new BadCredentialsException("Incorrect api key");
        });
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader("x-api-key");
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
