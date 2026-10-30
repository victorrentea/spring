package victor.training.spring.async;

import io.github.resilience4j.springboot3.retry.autoconfigure.RetryAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// max-attempts comes from application.properties; only the wait is shortened to keep the test fast
@SpringBootTest(classes = {DrinksClient.class, DrinksClientRetryTest.Config.class},
    properties = "resilience4j.retry.instances.drinks.wait-duration=10ms")
@ImportAutoConfiguration({AopAutoConfiguration.class, RetryAutoConfiguration.class})
class DrinksClientRetryTest {
  private static final String VODKA_URL = "http://localhost:8080/api/vodka";

  @TestConfiguration
  static class Config {
    @Bean
    RestClient.Builder restClientBuilder() {
      return RestClient.builder();
    }

    @Bean
    MockRestServiceServer server(RestClient.Builder builder) {
      return MockRestServiceServer.bindTo(builder).build();
    }

    @Bean
    RestClient restClient(RestClient.Builder builder, MockRestServiceServer server) {
      return builder.build(); // built after the server is bound
    }
  }

  @MockitoBean
  RestTemplate restTemplate;
  @MockitoBean
  DrinksApiFeignClient drinksApiFeignClient;

  @Autowired
  DrinksClient drinksClient;
  @Autowired
  MockRestServiceServer server;

  @BeforeEach
  void resetServer() {
    server.reset();
  }

  @Test
  void succeedsAtThirdAttempt() {
    server.expect(times(2), requestTo(VODKA_URL)).andRespond(withServerError());
    server.expect(once(), requestTo(VODKA_URL))
        .andRespond(withSuccess("{\"type\":\"deadly\"}", MediaType.APPLICATION_JSON));

    assertThat(drinksClient.pourVodka().getType()).isEqualTo("deadly");
    server.verify();
  }

  @Test
  void givesUpAfterMaxAttempts() {
    server.expect(times(3), requestTo(VODKA_URL)).andRespond(withServerError());

    assertThatThrownBy(() -> drinksClient.pourVodka())
        .isInstanceOf(HttpServerErrorException.class);
    server.verify();
  }

  @Test
  void doesNotRetryClientErrors() {
    server.expect(once(), requestTo(VODKA_URL)).andRespond(withBadRequest());

    assertThatThrownBy(() -> drinksClient.pourVodka())
        .isInstanceOf(HttpClientErrorException.class);
    server.verify();
  }
}
