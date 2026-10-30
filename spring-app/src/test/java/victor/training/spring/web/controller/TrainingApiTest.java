package victor.training.spring.web.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static victor.training.spring.web.TestDataUtil.javaTraining;

// starts the whole app on a random port, over an in-memory H2, and calls it over real HTTP
@SpringBootTest(webEnvironment = RANDOM_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:training-api-test")
// a fake teacher-bio service on a random port: our Feign client really calls it over HTTP
@EnableWireMock(@ConfigureWireMock(name = "teacher-bio", baseUrlProperties = "teacher.bio.uri.base",
        filesUnderClasspath = "wiremock/teacher-bio")) // loads mappings/*.json from there
class TrainingApiTest {
  @Autowired
  TestRestTemplate rest; // e deja configurat cu localhost:<portu-random>
  @Autowired
  TrainingRepo trainingRepo;
  @Autowired
  TeacherRepo teacherRepo;
  @InjectWireMock("teacher-bio")
  WireMockServer teacherBioService;

  Teacher alice;
  Teacher bob;

  @BeforeEach
  void logIn() {
    rest = rest.withBasicAuth("user", "user");
  }

  @Test
  void returnsTrainingsWithTheirDetailsInInsertionOrder() {
    trainingRepo.deleteAll(); // the ones InitialData inserted at startup
    alice = teacherRepo.save(new Teacher("Alice"));
    bob = teacherRepo.save(new Teacher("Bob"));
    trainingRepo.save(javaTraining().teacher(alice));
    trainingRepo.save(new Training()
            .name("Kotlin")
            .startDate(LocalDate.of(2026, 12, 1))
            .description("Coroutines")
            .programmingLanguage(ProgrammingLanguage.KOTLIN)
            .teacher(bob));

    ResponseEntity<TrainingDto[]> response = rest.getForEntity("/api/trainings", TrainingDto[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    TrainingDto[] trainings = response.getBody();
    assertThat(trainings).hasSize(2);

    TrainingDto java = trainings[0];
    assertThat(java.name).isEqualTo("Java");
    assertThat(java.description).isEqualTo("Java description");
    assertThat(java.startDate).isEqualTo(LocalDate.of(2026, 11, 1));
    assertThat(java.teacherId).isEqualTo(alice.id());
    assertThat(java.teacherName).isEqualTo("Alice");
    assertThat(java.language).isEqualTo(ProgrammingLanguage.JAVA);

    TrainingDto kotlin = trainings[1];
    assertThat(kotlin.name).isEqualTo("Kotlin");
    assertThat(kotlin.description).isEqualTo("Coroutines");
    assertThat(kotlin.startDate).isEqualTo(LocalDate.of(2026, 12, 1));
    assertThat(kotlin.teacherId).isEqualTo(bob.id());
    assertThat(kotlin.teacherName).isEqualTo("Bob");
    assertThat(kotlin.language).isEqualTo(ProgrammingLanguage.KOTLIN);
  }

  @Test
  void returnsAnEmptyListWhenThereAreNoTrainings() {
    trainingRepo.deleteAll();

    TrainingDto[] trainings = rest.getForObject("/api/trainings", TrainingDto[].class);

    assertThat(trainings).isEmpty();
  }

  @Test
  void returnsOneTrainingWithItsTeacherBio() {
    alice = teacherRepo.save(new Teacher("Alice"));
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
    alice = teacherRepo.save(new Teacher("Alice"));
    Long id = trainingRepo.save(javaTraining().teacher(alice)).id();
    teacherBioService.stubFor(get(urlEqualTo("/api/teachers/" + alice.id() + "/bio"))
            .willReturn(serverError()));

    ResponseEntity<TrainingDto> response = rest.getForEntity("/api/trainings/{id}", TrainingDto.class, id);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().name).isEqualTo("Java");
    assertThat(response.getBody().teacherBio).startsWith("<ERROR RETRIEVING TEACHER BIO");
  }

  @Test
  void searchesByNameGivenAsQueryParam() {
    trainingRepo.deleteAll();
    alice = teacherRepo.save(new Teacher("Alice"));
    trainingRepo.save(javaTraining().teacher(alice));
    trainingRepo.save(new Training().name("Kotlin").teacher(alice));

    TrainingDto[] trainings = rest.getForObject("/api/trainings/search2?name=Java", TrainingDto[].class);

    assertThat(trainings).extracting(t -> t.name).containsExactly("Java");
  }
}
