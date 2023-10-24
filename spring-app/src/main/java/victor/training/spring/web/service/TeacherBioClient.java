package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
  @Cacheable("teacher-bio") // imagine hashMap.put(long teacherId, String);
  public String retrieveBiographyForTeacher(long teacherId) {
    log.debug("Calling external web endpoint... (takes time)");
//    String result = dummyCall(teacherId);
    String result = callUsingFeignClient(teacherId);
//    String result = callUsingRestTemplate(teacherId);
    log.debug("Got result");
    return result;
  }
  record TeacherBioChangedEvent(long teacherId) {}

  @Autowired
  private CacheManager cacheManager;

  //  @RabbitListener
//  @CacheEvict("teacher-bio") //Will NOT work imagine hashMap.remove(record event); // not evict evict anything ever
  @CacheEvict(value = "teacher-bio",key = "#event.teacherId()")
  public void onTeacherBioChanged(TeacherBioChangedEvent event) {
    // empty function. don't delete. let the proxy magic happen!
//    cacheManager.getCache("anotherCache").evictIfPresent(event.teacherId());
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

    // Auth#1 :) - no bearer
//    String bearerToken = "joke";

    // Auth#2 - propagating MY clients' AccessToken
    KeycloakPrincipal<KeycloakSecurityContext> principal =
            (KeycloakPrincipal<KeycloakSecurityContext>)
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String accessToken = principal.getKeycloakSecurityContext().getTokenString();

    // Auth#3 - manually creating a JWT token
    //          String bearerToken = Jwts.builder()
    //                .setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
    //                .claim("country", "Country")
    //                .signWith(SignatureAlgorithm.HS512, jwtSecret)
    //                .compact();


    log.info("Sending bearer: {}", accessToken);
    Map<String, List<String>> header = Map.of("Authorization", List.of("Bearer " + accessToken));
    ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
            CollectionUtils.toMultiValueMap(header),
            HttpMethod.GET,
            new URI(teacherBioUriBase + "/api/teachers/" + teacherId + "/bio")), String.class);
    return response.getBody();
  }

}

