package victor.training.spring.security.jwt.app;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Slf4j
public class DatabaseUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		JwtPrincipal principal = (JwtPrincipal) token.getPrincipal();

		String username = principal.getUsername();
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("No username provided");
		}

		log.debug("Lookup username {} in database", username);
		SecurityUser securityUser = userRepository.findByUsername(username);

		if (securityUser == null) {
			throw new UsernameNotFoundException("UserVO " + username + " not in database");
		} else {
			log.debug("UserVO found in database");
			if (!securityUser.isEnabled()) {
				throw new UsernameNotFoundException("UserVO is inactive in the database");
			}
			securityUser.setCountry(principal.getCountry());
		}

		log.debug("Successful login");
		return securityUser;
	}


}
