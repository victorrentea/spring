package victor.training.spring.ai;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static victor.training.spring.ai.AiApp.SYSTEM_PROMPT;

@RestController
public class AssistantController {
  private final ChatClient ai;
  private final DogRepo dogRepo;
  private final EmbeddingService embeddingService;

  AssistantController(
      ChatClient.Builder ai,
      PgVectorStore vectorStore,
      AdoptionSchedulerTool adoptionSchedulerTool,
      McpSyncClient smsSenderTool,
      DogRepo dogRepo,
      EmbeddingService embeddingService)  {

    this.ai = ai
        .defaultSystem(SYSTEM_PROMPT)
        .defaultTools(adoptionSchedulerTool)
        .defaultToolCallbacks(SyncMcpToolCallbackProvider.builder().mcpClients(smsSenderTool).build())
        .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
        .build();
    this.dogRepo = dogRepo;
    this.embeddingService = embeddingService;
  }

  Map<String, PromptChatMemoryAdvisor> chatMemoryPerUser = new ConcurrentHashMap<>();

  @GetMapping(value = "/{username}/assistant", produces = "text/markdown")
  // you can also use SSE without webflux
  Flux<String> assistant(@PathVariable String username, @RequestParam String q) {
    // TODO 2:✅ return Flux<String> for better UX
    // TODO 3:✅ add chat memory advisor per username
    // TODO 5:✅ add a system prompt with the current username
    PromptChatMemoryAdvisor memoryAdvisor = chatMemoryPerUser.computeIfAbsent(username, k -> memoryAdvisor());

    return ai.prompt()
        .system("The user's username is \"%s\"".formatted(username)) // system-prompt from SecurityContextHolder...
        .user(q)
        .advisors(memoryAdvisor)
        .stream()
        .content();
  }

  private PromptChatMemoryAdvisor memoryAdvisor() {
    return PromptChatMemoryAdvisor.builder(
        MessageWindowChatMemory.builder()
            .chatMemoryRepository(new InMemoryChatMemoryRepository()) // also could be Redis, SQL, Cassandra...
            .build())
        .build();
  }

  record DogSearchResultDto(Long id, String name, String breed) {}

  @GetMapping(value = "/{username}/search")
  List<DogSearchResultDto> search(@PathVariable String username, @RequestParam String q) {
    // TODO 7: get structured JSON data from AI. tell it in a system prompt.
    //  then .entity(new ParameterizedTypeReference<>() {});
    return ai.prompt()
        .system("Based on the user query, return ONLY a JSON array of objects, NOT a single JSON object.")
        .user(q)
        .call()
        .entity(new ParameterizedTypeReference<>() {});
  }

  // create a delete api that cancels a resevation for a dog id and a user.
  // mind to restore in vector store that dog back
  @DeleteMapping("/{username}/cancel/{dogId}")
  String cancelReservation(@PathVariable String username, @PathVariable Integer dogId) {
    var dog = dogRepo.findById(dogId).orElseThrow();
    if (dog.getOwner() == null || !dog.getOwner().equals(username)) {
      throw new IllegalStateException("Dog with id %d is not reserved by user %s".formatted(dogId, username));
    }
    dog.setOwner(null);
    dogRepo.save(dog);
    embeddingService.addDog(dog);
    return "Reservation cancelled for dog id %d by user %s".formatted(dogId, username);
  }
}
