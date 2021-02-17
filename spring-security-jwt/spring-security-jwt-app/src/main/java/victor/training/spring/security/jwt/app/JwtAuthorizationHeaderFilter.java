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
import javax.xml.bind.DatatypeConverter;

@Slf4j
public class JwtAuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
	@Value("${jwt.secret}")
	private String jwtSecret;
	@Value("${jwt.header}")
	private String jwtHeader;

	public JwtAuthorizationHeaderFilter(AuthenticationManager authenticationManager) {
		setAuthenticationManager(authenticationManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String jwtTokenString = request.getHeader(jwtHeader);
        if (jwtTokenString == null) {
			log.warn("Header {} not set", jwtHeader);
            return null;
        }

        log.debug("Received Header: " + jwtTokenString);
		log.debug("Hint: Try to decode it on http://jwt.io/");

        try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
					.parseClaimsJws(jwtTokenString)
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