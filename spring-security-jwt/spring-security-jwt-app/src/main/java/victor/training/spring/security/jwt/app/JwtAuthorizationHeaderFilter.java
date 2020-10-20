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

//public class JwtAuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
//	Claims claims = Jwts.parser()
//		.setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
//		.parseClaimsJws(jwtToken)
//		.getBody();