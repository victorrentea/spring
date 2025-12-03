package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.varie.Sleep;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class TeacherBioClient {
  @Value("${jwt.signature.shared.secret.base64}")
  private final String jwtSecret;
  @Value("${teacher.bio.uri.base}")
  private final String teacherBioUriBase;

  private final TeacherBioFeignClient feignClient;
  private final RestTemplate rest;


  // TODO cacheable
  public String retrieveBiographyForTeacher(long teacherId) {
    log.debug("Calling external web endpoint... (takes time)");
//    String result = dummyCall(teacherId);
    String result = feignClient.getTeacherBio(teacherId);
//    String result = callUsingRestTemplate(teacherId);
    log.debug("Got result");
    return result;
  }

  private String dummyCall(long teacherId) {
    Sleep.millis(500);
    return "Amazing bio for teacher " + teacherId;
  }

  @SneakyThrows
  private String callUsingRestTemplate(long teacherId) {
    Map<String, List<String>> headerMap = Map.of();
    ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
        CollectionUtils.toMultiValueMap(headerMap),
        HttpMethod.GET,
        new URI(teacherBioUriBase + "/api/teachers/" + teacherId + "/bio")), String.class);
    return response.getBody();
  }
}

