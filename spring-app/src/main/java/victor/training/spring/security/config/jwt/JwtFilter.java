package victor.training.spring.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import victor.training.spring.web.entity.UserRole;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;


// this sticks in the big chain of security filters of spring : https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-security-filters
@Slf4j
public class JwtFilter extends AbstractPreAuthenticatedProcessingFilter {
  @Value("${jwt.signature.shared.secret.base64}")
  private String jwtSecret;

  public JwtFilter(AuthenticationManager authenticationManager) {
    setAuthenticationManager(authenticationManager);
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    String headerValue = request.getHeader("Authorization");
    if (headerValue == null || !headerValue.startsWith("Bearer ")) {
       log.error("FAIL: No authorization bearer header: " + headerValue);
      throw new PreAuthenticatedCredentialsNotFoundException("No 'Authorization: Bearer ' header: " + headerValue); // reject
    }
    String jwtTokenString = headerValue.substring("Bearer ".length());
    log.debug("Received JWT token string: " + jwtTokenString);

    Algorithm algorithm = Algorithm.HMAC256(Base64.getDecoder().decode(jwtSecret));

    JWTVerifier verifier = JWT.require(algorithm).withIssuer("Victor").build();

    DecodedJWT decodedJwt = verifier.verify(jwtTokenString);

    UserRole role = UserRole.valueOf(decodedJwt.getClaim("role").asString());
    String country = decodedJwt.getClaim("country").asString();
    String[] authorities = decodedJwt.getClaim("authorities").asArray(String.class);
    String username = decodedJwt.getSubject();
    JwtPrincipal jwtPrincipal = new JwtPrincipal(username, country, role, List.of(authorities));
    log.info("Login successful for " + jwtPrincipal);
    return jwtPrincipal; // later received by UserDetailsService
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "N/A";
  }
}