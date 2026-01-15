package victor.training.spring.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class AdoptionSchedulerTool {
  private final DogRepo dogRepo;
  private final PgVectorStore vectorStore;

//  String scheduleAdoption(int dogId, String dogName, String username) {
//    log.info("Scheduling adoption for dog {} with id {} for user {}", dogName, dogId, username);
//    // TODO assign owner in DOG table
//    // TODO remove from vector store
//    // TODO return a pickup date of 3 days from now
//  }
}
