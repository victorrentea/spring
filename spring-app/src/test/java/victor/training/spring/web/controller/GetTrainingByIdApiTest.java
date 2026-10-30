package victor.training.spring.web.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wiremock.spring.InjectWireMock;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Teacher;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static victor.training.spring.web.TestDataUtil.javaTraining;

// GET /api/trainings/{id}
class GetTrainingByIdApiTest extends ApiTestBase {
  @InjectWireMock("teacher-bio")
  WireMockServer teacherBioService;

  @Test
  void returnsOneTrainingWithItsTeacherBio() {
    Teacher alice = teacherRepo.save(new Teacher("Alice"));
    Long id = trainingRepo.save(javaTraining().teacher(alice)).id();
    // the stub comes from src/test/resources/wiremock/teacher-bio/mappings/teacher-bio.json
    // the same stub, programmatically:
    // teacherBioService.stubFor(get(urlEqualTo("/api/teachers/" + alice.getId() + "/bio"))
    //         .willReturn(okForContentType("text/plain", "Alice teaches Java")));

    //Există pe net o chestie care se numește WireMock Recorder Proxy, care e un program prin care, dacă treci traficul către un sistem terț, ăla îți salvează request-response, request-response, request-response în JSON-uri ca cel de aici, pe care apoi le poți folosi pentru a reda răspunsurile lor fără să le mai bombardezi efactura.


    ResponseEntity<TrainingDto> response = rest.getForEntity("/api/trainings/{id}", TrainingDto.class, id);

    teacherBioService.verify(getRequestedFor(urlEqualTo("/api/teachers/" + alice.id() + "/bio")));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    TrainingDto training = response.getBody();
    assertThat(training.id).isEqualTo(id);
    assertThat(training.name).isEqualTo("Java");
    assertThat(training.description).isEqualTo("Java description");
    assertThat(training.startDate).isEqualTo(LocalDate.of(2026, 11, 1));
    assertThat(training.teacherId).isEqualTo(alice.id());
    assertThat(training.teacherName).isEqualTo("Alice");
    assertThat(training.teacherBio).isEqualTo("Alice teaches Java");
    assertThat(training.language).isEqualTo(ProgrammingLanguage.JAVA);
    assertThat(training.canDelete).isFalse(); // "user" lacks TRAINING_DELETE
  }

  @Test
  void returnsTheTrainingEvenWhenTheTeacherBioServiceFails() {
    Teacher alice = teacherRepo.save(new Teacher("Alice"));
    Long id = trainingRepo.save(javaTraining().teacher(alice)).id();
    teacherBioService.stubFor(get(urlEqualTo("/api/teachers/" + alice.id() + "/bio"))
            .willReturn(serverError()));

    ResponseEntity<TrainingDto> response = rest.getForEntity("/api/trainings/{id}", TrainingDto.class, id);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().name).isEqualTo("Java");
    assertThat(response.getBody().teacherBio).startsWith("<ERROR RETRIEVING TEACHER BIO");
  }
}
