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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Slf4j
@RestController
public class GatewayController {

    @Value("${jwt.secret}")
    private String jwtSecret;

//    public static final String JWT_HEADER_NAME = "x-mycomp-jwt-1";

    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/resource")
    public String update() {
        return "Updated at " + LocalDateTime.now().toString() + "<br>(if no request is blocked add these to windows\\system32\\drivers\\etc\\hosts: 127.0.0.1 racheta\n" +
                "127.0.0.1 marte";

        // XXX if no request is blocked add these to windows\system32\drivers\etc\hosts:
        // 127.0.0.1 racheta
        // 127.0.0.1 marte

        // and load the page from marte:8080/cors.html
    }

    // the authorization server (e.g. a proxy, ZUUL?) does:
    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "test") String user,
            @RequestParam(defaultValue = "LOW") String level
            ) throws URISyntaxException {

        log.debug("Sending user id {}", user);
        AuthnContext authnContext = AuthnContext.valueOf(level);


        String jwtToken = Jwts.builder()
                .setSubject(user)
                .claim("countryId", "RO")
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        log.debug("JWT: " + jwtToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
//        headers.set(JWT_HEADER_NAME, jwtToken);

        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET,
                new URI("https://localhost:8081/rest"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return "Got: " + responseEntity.getBody() + " <br>Try adding ?user=<uid>";
        // Want to magically propagate JWT to subsequent REST calls it over thread :https://stackoverflow.com/questions/46729203/propagate-http-header-jwt-token-over-services-using-spring-rest-template
    }
}
