package victor.training.spring.security.config.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {
  public ApiKeyFilter(String expectedApiKey) {
    setAuthenticationManager(authentication -> {
      if (authentication.getPrincipal() instanceof String apiKeyFromHeader) {
        if (expectedApiKey.equals(apiKeyFromHeader)) {
          authentication.setAuthenticated(true);
          return authentication;
        } else {
          throw new BadCredentialsException("Incorrect api key: " + apiKeyFromHeader + " vs " + expectedApiKey); // ⚠️NOT IN PORD
        }
      }
      throw new BadCredentialsException("Missing api key: " + authentication.getPrincipal());
    });
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
    return httpRequest.getHeader("x-api-key");
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "N/A";
  }
}
