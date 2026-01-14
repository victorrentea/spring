package victor.training.spring.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
// From https://www.youtube.com/watch?v=9mOuvrZtLbc&t=2s
public class AssistantController {
  public static final String SYSTEM_PROMPT = """
      You are an AI powered assistant to help people adopt a dog from the adoption
      agency named Pooch Palace with locations in Antwerp, Seoul, Tokyo, Singapore, Paris,
      Mumbai, New Delhi, Barcelona, San Francisco, and London. Information about the dogs available
      will be presented below. If there is no information, then return a polite response suggesting we
      don’t have any dogs available. To adopt a dog, the user must be sent an SMS with the details about the pickup.
      """;

  private final ChatClient ai;
  private final Map<String, PromptChatMemoryAdvisor> memory = new ConcurrentHashMap<>(); // or in Redis, SQL, Cassandra...

  AssistantController(
      ChatClient.Builder ai,
      VectorStore vectorStore,
      AdoptionSchedulerTool adoptionSchedulerTool
//      McpSyncClient mcpSyncClient
  )  {

    this.ai = ai
        .defaultSystem(SYSTEM_PROMPT) // assumed role
//        .defaultToolCallbacks(SyncMcpToolCallbackProvider.builder().mcpClients(mcpSyncClient).build()) // remote call to send SMS
        .defaultTools(adoptionSchedulerTool)
        .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build()/*RAG dintr-un general purpose dog characteristics json 2MB*/)
        .build();
  }

  @GetMapping(value = "/{username}/assistant",produces = "text/markdown")
  Flux<String> assistant(@PathVariable String username, @RequestParam String q) {
    var chatMemoryAdvisor = memory.computeIfAbsent(username, k -> memoryAdvisor());

    // TODO de ce nu pot sa-i spun "vreau primul"??
    return ai.prompt()
        .system("The user's username is \"%s\"".formatted(username)) // system-prompt from SecurityContextHolder...
        .user(q) // user-prompt
        .advisors(chatMemoryAdvisor)
        .stream()
        .content();
  }

  private PromptChatMemoryAdvisor memoryAdvisor() {
    return PromptChatMemoryAdvisor.builder(
        MessageWindowChatMemory.builder()
            .chatMemoryRepository(new InMemoryChatMemoryRepository())
            .build())
        .build();
  }

  record DogSearchResultDto(int id, String name, String breed) {}

  @GetMapping(value = "/{username}/search")
  List<DogSearchResultDto> search(@PathVariable String username, @RequestParam String q) {
    return ai.prompt()
        .system("Based on the user query, return ONLY a JSON array of objects, NOT an JSON object.")
        .user(q)
        .call()
        .entity(new ParameterizedTypeReference<List<DogSearchResultDto>>() {});
  }

}

