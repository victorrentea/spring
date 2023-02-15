package victor.training.spring.web.security.jwt;

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


// this sticks in the big chain of security filters of spring : https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-security-filters
@Slf4j
public class JwtAuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
    @Value("${jwt.signature.shared.secret.base64}")
    private String jwtSecret;

    public JwtAuthorizationHeaderFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String headerValue = request.getHeader("Authorization");
        if (headerValue == null || !headerValue.startsWith("Bearer ")) {
//            log.warn("No authorization bearer header: " + headerValue);
            throw new PreAuthenticatedCredentialsNotFoundException("No 'Authorization: Bearer ' header: " + headerValue); // reject
        }
        String jwtTokenString = headerValue.substring("Bearer ".length());
        log.debug("Received JWT token string (decodable on http://jwt.io/): " + jwtTokenString);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(jwtSecret))
                    .parseClaimsJws(jwtTokenString)
                    .getBody();

            // extra claim
            String country = (String) claims.get("phone");
            String role = (String) claims.get("role");

            log.info("Attempting login with user={} and country={}", claims.getSubject(), country);
            return new JwtPreauthPrincipal(claims.getSubject(), country, role); // will be later picked by UserDetailsService
        } catch (UnsupportedJwtException jwtException) {
            throw new PreAuthenticatedCredentialsNotFoundException("Invalid JWT Token", jwtException);
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}