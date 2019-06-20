package hello;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
public class GatewayController {

    @Value("${jwt.secret}")
    private String jwtSecret;

//    public static final String JWT_HEADER_NAME = "x-mycomp-jwt-1";

    @Autowired
    private RestTemplate restTemplate;

    // in the authorization server (e.g. a proxy?)
    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "test") String user,
            @RequestParam(defaultValue = "LOW") String level
            ) throws URISyntaxException {

        log.debug("Sending user id {}", user);
        AuthnContext authnContext = AuthnContext.valueOf(level);
        String jwtToken = Jwts.builder()
                .setSubject(user)
                .claim("AuthnContext", authnContext.name())
                .claim("AuthnContext2", authnContext.name())
                .claim("AuthnContext3", authnContext.name())
                .claim("AuthnContext4", authnContext.name())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
//        headers.set(JWT_HEADER_NAME, jwtToken);
        log.debug("JWT: " + jwtToken);

        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET,
                new URI("http://localhost:8081/rest"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);


        return "Got: " + responseEntity.getBody() + " <br>Try adding ?user=<uid>";
        //some idea for propagating it over thread :https://stackoverflow.com/questions/46729203/propagate-http-header-jwt-token-over-services-using-spring-rest-template
    }
}
