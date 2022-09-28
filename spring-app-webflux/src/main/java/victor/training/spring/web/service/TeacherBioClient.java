package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TeacherBioClient {
    @Value("${jwt.signature.shared.secret.base64}")
    private String jwtSecret;
    @Value("${teacher.bio.uri.base}")
    private String teacherBioUriBase;

    public Mono<String> retrieveBiographyForTeacher(long teacherId) {
        log.debug("Calling external web endpoint... (takes time)");
        return WebClient.create()
                .get()
                .uri(teacherBioUriBase + "/api/teachers/" + teacherId + "/bio")
                .retrieve()
                .bodyToMono(String.class);
    }

}
