package victor.training.spring.security.jwt.app;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class DatabaseUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

   @Autowired
   private UserRepository userRepository;
   @Override
   public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

      JwtPrincipal principal = (JwtPrincipal) token.getPrincipal();

      SecurityUser user = userRepository.findByUsername(principal.getUsername());

      if (user == null) {
         throw new UsernameNotFoundException(principal.getUsername());
      }
      user.setCountry(principal.getCountry());
      return user;
   }
}
