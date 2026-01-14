package victor.training.spring.ai;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
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

import static victor.training.spring.ai.AiApp.SYSTEM_PROMPT;


@RestController
public class AssistantController {
  private final ChatClient ai;
  private final Map<String, PromptChatMemoryAdvisor> memory = new ConcurrentHashMap<>(); // or in Redis, SQL, Cassandra...

  AssistantController(
      ChatClient.Builder ai,
      VectorStore vectorStore,
      AdoptionSchedulerTool adoptionSchedulerTool,
      McpSyncClient smsSenderTool // remote call to send SMS
  )  {

    this.ai = ai
        .defaultSystem(SYSTEM_PROMPT) // assumed role
        .defaultTools(adoptionSchedulerTool) // local tool
        .defaultToolCallbacks(SyncMcpToolCallbackProvider.builder().mcpClients(smsSenderTool).build()) // remote call to send SMS
        .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build()/*RAG dintr-un general purpose dog characteristics json 2MB*/)
        .build();
  }

  @GetMapping(value = "/{username}/assistant", produces = "text/markdown")
  Flux<String> assistant(@PathVariable String username, @RequestParam String q) {
    var chatMemoryAdvisor = memory.computeIfAbsent(username, k -> memoryAdvisor());

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
        .entity(new ParameterizedTypeReference<>() {});
  }
}

