package victor.training.spring.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
  private final PgVectorStore vectorStore;

  public void addDog(Dog dog) {
    vectorStore.add(List.of(embed(dog)));
  }

  public void removeDog(Dog dog) {
    vectorStore.delete(List.of(dog.getVectorId()));
  }

  Document embed(Dog dog) {
    String content = "id: %d, name: %s, description: %s".formatted(
        dog.getId(), dog.getName(), dog.getDescription()
    );
    return new Document(dog.getVectorId(), content, Map.of());
  }
}
