package victor.training.spring.security.config.apikey;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.List;

@Slf4j
public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {
  public ApiKeyFilter(String expectedApiKey) {
    setAuthenticationManager(authentication -> acceptApiKeyUser(expectedApiKey, authentication));
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
    String apikey = httpRequest.getHeader("x-api-key");
//    return apikey; // return principal name = api key
    User user = new User(apikey,
            "N/A",
            List.of(new SimpleGrantedAuthority("ROLE_ACTUATOR")));
    return user;
  }

  private Authentication acceptApiKeyUser(String expectedApiKey, Authentication authentication) {
    if (authentication.getPrincipal() instanceof User u &&
        expectedApiKey.equals(u.getUsername())) {
      authentication.setAuthenticated(true);
      log.info("✅");
      return authentication;
    }

    throw new BadCredentialsException("Missing or incorrect api key: " + authentication.getPrincipal());
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "N/A";
  }
}
