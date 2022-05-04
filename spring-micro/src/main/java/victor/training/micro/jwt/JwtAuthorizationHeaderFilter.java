package victor.training.micro.jwt;

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
            String country = (String) claims.get("country");

            log.info("Attempting login with user={} and country={}", claims.getSubject(), country);
            return new JwtPreauthPrincipal(claims.getSubject(), country);
        } catch (UnsupportedJwtException jwtException) {
            throw new PreAuthenticatedCredentialsNotFoundException("Invalid JWT Token", jwtException);
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}