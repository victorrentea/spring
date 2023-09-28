package victor.training.spring.web.service;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.varie.ThreadUtils;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

@RequiredArgsConstructor
@Component
@Slf4j
public class TeacherBioClient {
  @Value("${jwt.signature.shared.secret.base64}")
  private final String jwtSecret;
  @Value("${teacher.bio.uri.base}")
  private final String teacherBioUriBase;

  private final TeacherBioFeignClient feignClient;

  // don't do "new RestTemplate()" but take it from Spring, to allow Sleuth to propagate 'traceId'
  private final RestTemplate rest;


  // TODO cacheable
  /// TODO: cum masor cat dureaza metoda asta ?
  // poti monitoriza cu bani grei cu DynaTrace si DataDog sau gratis cu Micrometer/Grafana
  @Timed
  // best practice @Timed pe orice
  // -  apel de API
  // -  zona CPU intensive
  // -  SQL huge ("fat pigs")
  public String retrieveBiographyForTeacher(long teacherId) {
    log.debug("Calling external web endpoint... (takes time)");
//    String result = dummyCall(teacherId);
    String result = callUsingRestTemplate(teacherId);
//    String result = callUsingFeignClient(teacherId);
    log.debug("Got result");
    return result;
  }

  private String dummyCall(long teacherId) {
    ThreadUtils.sleepMillis(500);
    return "Amazing bio for teacher " + teacherId;
  }

  @SneakyThrows
  public String callUsingFeignClient(long teacherId) {
    return feignClient.registerSheep(teacherId);
  }

  @SneakyThrows
  public String callUsingRestTemplate(long teacherId) {
    Map<String, List<String>> header = Map.of();// Map.of("Authorization", List.of("Bearer " + accessToken));
    ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
            CollectionUtils.toMultiValueMap(header),
            HttpMethod.GET,
            new URI(teacherBioUriBase + "/api/teachers/" + teacherId + "/bio")), String.class);
    return response.getBody();
  }

}

