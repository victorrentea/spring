package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class TeacherBioClient {
    @Value("${jwt.signature.shared.secret.base64}")
    private String jwtSecret;
    @Value("${teacher.bio.uri.base}")
    private String teacherBioUriBase;

    // TODO cacheable
    public Mono<String> retrieveBio(long teacherId) {
        try {
            log.info("Sending bearer");
            return WebClient.create()
                    .get()
                    .uri(new URI(teacherBioUriBase + "/api/teachers/" + teacherId + "/bio"))
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(body -> log.debug("Got back response : " + body));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    RestTemplate rest; // don't do "new RestTemplate()" but take it from Spring, to allow Sleuth to propagate 'traceId'


}
