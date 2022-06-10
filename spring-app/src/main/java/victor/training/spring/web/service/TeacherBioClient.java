package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.ThreadUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TeacherBioClient {
    @Value("${jwt.signature.shared.secret.base64}")
    private String jwtSecret;

    // TODO cacheable
    public String retrieveBiographyForTeacher(long teacherId) {
        log.debug("Calling external web endpoint... (takes time)");
        ThreadUtils.sleepq(500);
//        String result = dummy(teacherId);
        String result = real(teacherId);
        log.debug("Got result");
        return result;
    }

    private String dummy(long teacherId) {
        return "Amazing bio for teacher " + teacherId;
    }

    public String real(long teacherId) {
        try {
            RestTemplate rest = new RestTemplate();

            // 1 :)
            log.info("Sending bearer: JOKE");
            String bearerToken = "joke";

            // 2 OAuth2
//            log.info("Sending bearer: <KeyCloak>");
//            KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>)
//                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String bearerToken = principal.getKeycloakSecurityContext().getTokenString();

            // 3 Manual JWT
            //            log.info("Sending bearer: JOKE");
//            String bearerToken = createManualJwtToken();

            Map<String, List<String>> header = Map.of("Authorization", List.of("Bearer " + bearerToken));
            ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
                    CollectionUtils.toMultiValueMap(header),
                    HttpMethod.GET,
                    new URI("http://localhost:8082/api/teachers/" + teacherId + "/bio")), String.class);
            return response.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

//    private String createManualJwtToken() {
//        return Jwts.builder()
//                .setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
//                .claim("country", "Country")
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
}
