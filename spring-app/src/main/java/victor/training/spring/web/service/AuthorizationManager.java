package victor.training.spring.web.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class AuthorizationManager {
  public void checkUserCanDeleteTraining() {
    List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream().map(GrantedAuthority::getAuthority)
            .collect(toList());
    // any authority of a user in Spring Security is a role if it starts with "ROLE_"
    if (!authorities.contains("ROLE_ADMIN")) {
      throw new IllegalArgumentException("Hands Off from code !");
    }
  }
}
