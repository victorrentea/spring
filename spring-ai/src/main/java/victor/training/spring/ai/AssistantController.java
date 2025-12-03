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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
// From https://www.youtube.com/watch?v=9mOuvrZtLbc&t=2s
public class AssistantController {
  private final ChatClient ai;

  private final Map<String, PromptChatMemoryAdvisor> memory = new ConcurrentHashMap<>(); // in Redis, SQL, Mongo...

  AssistantController(
      ChatClient.Builder ai,
      VectorStore vectorStore,
      JobAdoptionScheduler jobAdoptionScheduler,
      McpSyncClient mcpSyncClient)  {

    String systemPrompt = """
        You are an AI powered assistant to help people adopt a dog from the adoption
        agency named Pooch Palace with locations in Antwerp, Seoul, Tokyo, Singapore, Paris,
        Mumbai, New Delhi, Barcelona, San Francisco, and London. Information about the dogs available
        will be presented below. If there is no information, then return a polite response suggesting we
        don’t have any dogs available. To adopt a dog, the user must be sent an SMS with the details about the pickup.
        """;
    this.ai = ai
        .defaultSystem(systemPrompt)
        .defaultToolCallbacks(SyncMcpToolCallbackProvider.builder().mcpClients(mcpSyncClient).build())
        .defaultTools(jobAdoptionScheduler)
        .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
        .build();
  }

  @GetMapping(value = "/{username}/assistant",produces = "text/markdown")
  String assistant(@PathVariable String username, @RequestParam String q) {
    var chatMemoryAdvisor = memory.computeIfAbsent(username, k -> newAdvisor());

    return ai.prompt()
        .system("The user's username is \"%s\"".formatted(username))
        .user(q)
        .advisors(chatMemoryAdvisor)
        .call()
        .content();
  }

  private PromptChatMemoryAdvisor newAdvisor() {
    return PromptChatMemoryAdvisor
        .builder(MessageWindowChatMemory.builder()
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

