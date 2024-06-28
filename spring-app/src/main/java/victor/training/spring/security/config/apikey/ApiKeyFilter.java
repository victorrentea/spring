package victor.training.spring.security.config.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.List;

public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {
  public ApiKeyFilter(List<String> expectedApiKey) {
    setAuthenticationManager(authentication -> acceptApiKeyUser(expectedApiKey, authentication));
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
    return httpRequest.getHeader("x-api-key"); // return principal name = api key
  }

  private Authentication acceptApiKeyUser(List<String> knownApiKeys, Authentication authentication) {
    if (authentication.getPrincipal() instanceof String apiKeyFromHeader &&
        knownApiKeys.contains(apiKeyFromHeader)) {
      authentication.setAuthenticated(true);
      return authentication;
    }
    throw new BadCredentialsException("Missing or incorrect api key: " + authentication.getPrincipal());
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "N/A";
  }
}
