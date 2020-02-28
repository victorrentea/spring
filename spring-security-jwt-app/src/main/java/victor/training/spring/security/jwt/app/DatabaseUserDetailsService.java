package victor.training.spring.security.jwt.app;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Slf4j
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		UsernameContextPrincipal principal = (UsernameContextPrincipal) token.getPrincipal();

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
		}

		log.debug("Allowing the request to get in");
//		return new MyUserWithContext<UserVO>(userVO.getUsername(), userVO, principal.getContext());
		return securityUser;
	}


}
