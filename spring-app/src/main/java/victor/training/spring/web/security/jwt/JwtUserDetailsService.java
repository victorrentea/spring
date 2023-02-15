package victor.training.spring.web.security.jwt;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Slf4j
public class JwtUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		JwtPreauthPrincipal principal = (JwtPreauthPrincipal) token.getPrincipal();

		String username = principal.getUsername();

		if (username == null || username.isBlank()) {
			throw new UsernameNotFoundException("No username provided");
		}

		// Variant: check user in your DB
//		log.debug("Lookup username {} in database", username);
//		SecurityUser securityUser = userRepository.findByUsername(username);
//		if (securityUser == null) {
//			throw new UsernameNotFoundException("UserVO " + username + " not in database");
//		}

		JwtUser jwtUser = new JwtUser(principal.getUsername(), "USER");
		jwtUser.setCountry(principal.getCountry());
		log.debug("Login successful");
		return jwtUser;
	}


}
