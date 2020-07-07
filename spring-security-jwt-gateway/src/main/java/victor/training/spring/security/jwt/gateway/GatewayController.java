package victor.training.spring.security.jwt.gateway;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GatewayController {
    private final RestTemplate restTemplate;

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.header}")
    private String jwtHeader;

    // TODO per-endpoint CORS policy via @CrossOrigin
    @RequestMapping("/resource")
    public String update() {
        return "Updated at " + LocalDateTime.now().toString();
        // XXX if no request is blocked add these to windows\system32\drivers\etc\hosts:
        // 127.0.0.1 racheta
        // 127.0.0.1 marte

        // and load the page from marte:8080/cors.html
    }

    // the authorization server (e.g. a proxy, ZUUL?) does:
    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "test") String user,
            @RequestParam(defaultValue = "RO") String country
            ) throws URISyntaxException {

        log.debug("Sending user id {}", user);


        String jwtToken = Jwts.builder()
                .setSubject(user)
                .claim("country", country)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        log.debug("JWT Token: " + jwtToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add(jwtHeader, jwtToken);
        // normal ar fi sa trimiti peste Authentication: Bearer <CARMAZ>

        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET,
                new URI("http://localhost:8080/ping"));
        // TODO switch to https
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return "Got: " + responseEntity.getBody() + " <br>Try adding ?user=<uid>";

        // Want to magically propagate JWT to subsequent REST calls it over thread :
        // https://stackoverflow.com/questions/46729203/propagate-http-header-jwt-token-over-services-using-spring-rest-template
    }
}
