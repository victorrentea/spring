package victor.training.spring.transaction.playground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;
import victor.training.sequence.DiagramStore;
import victor.training.sequence.SequenceDiagram;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * The experiments TransactionsApp runs at startup are drawn too: target/sequence/TransactionsApp.start.svg
 */
@SequenceDiagram
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:playground;DB_CLOSE_DELAY=-1")
class PlaygroundSequenceTest {
  @Autowired
  PlayJpa jpa;
  @Autowired
  DiagramStore diagrams;
  @LocalServerPort
  int port;

  @Test
  void writeBehind() {
    jpa.writeBehind();
  }

  @Test
  void anHttpRequestIsADiagramOfItsOwn() {
    RestClient.create("http://localhost:" + port).get().uri("/lazy").retrieve().toBodilessEntity();

    assertThat(diagrams.recent()).anySatisfy(d -> assertThat(d.name()).isEqualTo("GET /lazy"));
  }
}
