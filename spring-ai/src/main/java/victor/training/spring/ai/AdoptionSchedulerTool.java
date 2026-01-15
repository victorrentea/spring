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

  @Tool(description = "schedule an appointment to pickup or adopt a dog from a location, returning the date of the appointment")
  String scheduleAdoption(
      @ToolParam(description = "the dog id that is adopted") int dogId,
      @ToolParam(description = "the name of the dog") String dogName,
      @ToolParam(description = "the username of the adopter") String username) {

    log.info("Scheduling adoption for dog {} with id {} for user {}", dogName, dogId, username);
    Dog dog = dogRepo.findById(dogId).orElseThrow();
    if (dog.getOwner() != null) {
      throw new IllegalStateException("Dog with id %d is already adopted".formatted(dogId));
    }
    dog.setOwner(username);
    dogRepo.save(dog);
    vectorStore.delete(List.of(dog.getVectorId()));
    return LocalDate.now().plusDays(3).toString();
  }
}
