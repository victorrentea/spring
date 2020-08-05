package victor.training.spring.security.jwt.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.JwtSignatureValidator;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Base64;

public class Test {
   public static void main(String[] args) {
      System.out.println(Arrays.toString(DatatypeConverter.parseBase64Binary("secretMare")));
      System.out.println(Arrays.toString(DatatypeConverter.parseBase64Binary("secretMareX")));

      String jwtToken = Jwts.builder()
          .setSubject("jdoe")
          .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString("parola2".getBytes())) // parola
          .compact();

      Jws<Claims> jws = Jwts.parser()
          .setSigningKey(DatatypeConverter.parseBase64Binary(Base64.getEncoder().encodeToString("parola".getBytes()))) // parola2
          .parseClaimsJws(jwtToken);
      System.out.println(jws.getBody().getSubject());
//
//
//      JwtSignatureValidator
//       JWTVerifier verifier = JWT.require(algorithm)
//          .withIssuer("auth0")
//          .build(); //Reusable verifier instance
//      DecodedJWT jwt = verifier.verify(token);
//      System.out.println(claims.getSubject());
   }
}
