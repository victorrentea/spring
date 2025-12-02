package victor.training.spring.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InitialData {
  private final DogRepo dogRepo;
  private final PgVectorStore vectorStore;
  private final JdbcTemplate jdbcTemplate;
  @Value("${server.port:8080}")
  int serverPort;

  @EventListener(ApplicationStartedEvent.class)
  public void insertData() {
    if (dogRepo.count() > 0) {
      log.info("Skip initialization. DB contains {} dogs. To re-init click here: http://localhost:{}/restart", dogRepo.count(),serverPort);
      return;
    }
    // truncate table vector_store
    jdbcTemplate.execute("TRUNCATE TABLE vector_store");

    List<Dog> initialDogs = List.of(
        new Dog().setName("Fierce Chiwawa").setDescription("A small but brave dog breed known for its lively personality."),
        new Dog().setName("Gentle Golden Retriever").setDescription("A friendly and intelligent dog breed, great with families."),
        new Dog().setName("Playful Beagle").setDescription("A curious and merry dog breed, known for its excellent sense of smell."),
        new Dog().setName("Loyal German Shepherd").setDescription("A versatile and courageous dog breed, often used in police and military roles."),
        new Dog().setName("Energetic Border Collie").setDescription("A highly intelligent and energetic dog breed, excels in agility and obedience."),
        new Dog().setName("Affectionate Cavalier King Charles Spaniel").setDescription("A loving and gentle dog breed, perfect for companionship."),
        new Dog().setName("Alert Doberman Pinscher").setDescription("A sleek and powerful dog breed, known for its loyalty and protective instincts."));

    log.info("Start vectorization");

    for (Dog dog : initialDogs) {
      dogRepo.save(dog);

      String content = "id: %d, name: %s, description: %s".formatted(
          dog.getId(), dog.getName(), dog.getDescription()
      );
      String vectorId = dog.getVectorId();
      vectorStore.add(List.of(new Document(vectorId, content, Map.of())));
    }
    log.info("Done vectorization");
  }

  @GetMapping("restart")
  void restart() {
    dogRepo.deleteAll();
    insertData();
  }
}
