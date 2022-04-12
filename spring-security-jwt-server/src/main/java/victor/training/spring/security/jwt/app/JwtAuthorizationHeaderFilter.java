package victor.training.spring.security.jwt.app;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Slf4j
public class JwtAuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
	@Value("${jwt.secret}")
	private String jwtSecret;

	public JwtAuthorizationHeaderFilter(AuthenticationManager authenticationManager) {
		setAuthenticationManager(authenticationManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String headerValue = request.getHeader("Authorization");
        if (headerValue == null) {
			log.warn("Header {} not set", "Authorization");
            return null;
        }

		String jwtToken = headerValue.substring("Bearer ".length());
        log.debug("Received Header: " + jwtToken);
		log.debug("Hint: Try to decode it on http://jwt.io/");

        try {
			Claims claims = Jwts.parser()
					.setSigningKey(Base64.getDecoder().decode(jwtSecret))
					.parseClaimsJws(jwtToken)
					.getBody();

			String country = (String) claims.get("country");
			log.info("Attempting login with user={} and country={}", claims.getSubject(), country);
			return new JwtPrincipal(claims.getSubject(), country);
		} catch (UnsupportedJwtException jwtException) {
			throw new PreAuthenticatedCredentialsNotFoundException("Invalid JWT Token", jwtException);
		}
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}
}