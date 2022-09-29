package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@Component
@Slf4j
public class TeacherBioClient {
    @Value("${jwt.signature.shared.secret.base64}")
    private String jwtSecret;
    @Value("${teacher.bio.uri.base}")
    private String teacherBioUriBase;

    // TODO cacheable
    public String retrieveBiographyForTeacher(long teacherId) {
        log.debug("Calling external web endpoint... (takes time)");
        String result = real(teacherId);
        log.debug("Got result");
        return result;
    }

    @Autowired
    RestTemplate rest; // don't do "new RestTemplate()" but take it from Spring, to allow Sleuth to propagate 'traceId'

    public String real(long teacherId) {
        try {
            // 1 :)
            log.info("Sending bearer: JOKE");
            ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
                    CollectionUtils.toMultiValueMap(Collections.emptyMap()),
                    HttpMethod.GET,
                    new URI(teacherBioUriBase + "/api/teachers/" + teacherId + "/bio")), String.class);
            return response.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
