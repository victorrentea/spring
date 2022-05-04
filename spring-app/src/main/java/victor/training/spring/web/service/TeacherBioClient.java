package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

            KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              String accessToken = principal.getKeycloakSecurityContext().getTokenString();
//            String accessToken = "joke";
            Map<String, List<String>> header = Map.of("Authorization", List.of("Bearer " + accessToken));
            ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
                    CollectionUtils.toMultiValueMap(header),
                    HttpMethod.GET,
                    new URI("http://localhost:8082/api/teachers/" + teacherId + "/bio")), String.class);
            return response.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
