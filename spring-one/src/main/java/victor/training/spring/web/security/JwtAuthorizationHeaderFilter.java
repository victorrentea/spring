//package victor.training.spring.web.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.UnsupportedJwtException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.xml.bind.DatatypeConverter;
//
//@Slf4j
//public class JwtAuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
//	@Value("${jwt.secret}")
//	private String backendSecret;
//
//	public JwtAuthorizationHeaderFilter(AuthenticationManager authenticationManager) {
//		setAuthenticationManager(authenticationManager);
//	}
//
//	@Override
//	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//		String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//		log.debug("Received Header: " + authenticationHeader);
//		log.debug("Try to decode it on http://jwt.io/");
//		if (StringUtils.isBlank(authenticationHeader)) {
//			return null;
//		}
//		String jwtHeader = authenticationHeader.substring("Bearer ".length());
//
//        try {
//			Claims claims = Jwts.parser()
//					.setSigningKey(DatatypeConverter.parseBase64Binary(backendSecret))
//					.parseClaimsJws(jwtHeader)
//					.getBody();
//
//			String country = (String) claims.get("country");
//			log.info("Attempting login with user={} and country={}", claims.getSubject(), country);
//			return new JwtPrincipal(claims.getSubject(), country);
//		} catch (UnsupportedJwtException jwtException) {
//			throw new PreAuthenticatedCredentialsNotFoundException("Invalid JWT Token", jwtException);
//		}
//	}
//
//	@Override
//	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//		return "N/A";
//	}
//}