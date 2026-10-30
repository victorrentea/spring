package victor.training.spring.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// starts the whole app on a random port, over an in-memory H2, and calls it over real HTTP
// all subclasses share this config => Spring starts the app only once for all of them
@SpringBootTest(webEnvironment = RANDOM_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:training-api-test")
// a fake teacher-bio service on a random port: our Feign client really calls it over HTTP
@EnableWireMock(@ConfigureWireMock(name = "teacher-bio", baseUrlProperties = "teacher.bio.uri.base",
        filesUnderClasspath = "wiremock/teacher-bio")) // loads mappings/*.json from there
abstract class ApiTestBase {
  @Autowired
  TestRestTemplate rest; // e deja configurat cu localhost:<portu-random>
  @Autowired
  TrainingRepo trainingRepo;
  @Autowired
  TeacherRepo teacherRepo;

  @BeforeEach
  void logIn() {
    rest = rest.withBasicAuth("user", "user");
  }
}
