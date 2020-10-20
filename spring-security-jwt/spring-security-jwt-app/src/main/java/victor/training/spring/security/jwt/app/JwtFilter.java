package victor.training.spring.security.jwt.app;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Objects;

public class JwtFilter extends AbstractPreAuthenticatedProcessingFilter {
   private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

   public static final String BEARER_PREFIX = "Bearer ";
   @Value("${jwt.secret}")
   private String jwtSecret;

   public JwtFilter(AuthenticationManager authenticationManager) {
      setAuthenticationManager(authenticationManager);
   }

   @Override
   protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

      String authorization = Objects.requireNonNull(request.getHeader("Authorization"));
      log.info("Got header: "  + authorization);
      if (!authorization.startsWith(BEARER_PREFIX)) {
         throw new IllegalArgumentException("Not a valid header");
      }

      String jwtString = authorization.substring(BEARER_PREFIX.length());

      log.info("Got jwt: "  + jwtString);

      Claims body = Jwts.parser()
          .setSigningKey(jwtSecret)
          .parseClaimsJws(jwtString)
          .getBody();


      String country = (String) body.get("country");
      String subject = body.getSubject();
      log.info("Vine userul {} din tara {}", subject, country);


      return new JwtPrincipal(subject, country);
   }

   @Override
   protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
      //parola
      return "N/A";
   }
}
