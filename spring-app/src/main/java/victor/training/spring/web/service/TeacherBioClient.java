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

  // don't do "new RestTemplate()" but take it from Spring, to allow Sleuth to propagate 'traceId'
  private final RestTemplate rest;



  // TODO cacheable
  public String retrieveBiographyForTeacher(long teacherId) {
    log.debug("Calling external web endpoint... (takes time)");
//    String result = dummyCall(teacherId);
    String result = callUsingFeignClient(teacherId);
//    String result = callUsingRestTemplate(teacherId);
    log.debug("Got result");
    return result;
  }

  private String dummyCall(long teacherId) {
    Sleep.millis(500);
    return "Amazing bio for teacher " + teacherId;
  }

  @SneakyThrows
  public String callUsingFeignClient(long teacherId) {
    return feignClient.registerSheep(teacherId);
  }

  @SneakyThrows
  public String callUsingRestTemplate(long teacherId) {

    // Auth#1 :) - no bearer
//    String bearerToken = "joke";
// TODO FIX migration
    // Auth#2 - propagating MY clients' AccessToken
//    KeycloakPrincipal<KeycloakSecurityContext> principal =
//            (KeycloakPrincipal<KeycloakSecurityContext>)
//            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    String accessToken = principal.getKeycloakSecurityContext().getTokenString();

    // Auth#3 - manually creating a JWT token
    //          String bearerToken = Jwts.builder()
    //                .setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
    //                .claim("country", "Country")
    //                .signWith(SignatureAlgorithm.HS512, jwtSecret)
    //                .compact();
//    log.info("Sending bearer: {}", accessToken);
//    Map<String, List<String>> header = Map.of("Authorization", List.of("Bearer " + accessToken));


    Map<String, List<String>> header = Map.of();
    ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
            CollectionUtils.toMultiValueMap(header),
            HttpMethod.GET,
            new URI(teacherBioUriBase + "/api/teachers/" + teacherId + "/bio")), String.class);
    return response.getBody();
  }

}

