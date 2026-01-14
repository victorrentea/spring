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
      VectorStore vectorStore, // RAG of dog descriptions
      AdoptionSchedulerTool adoptionSchedulerTool, // local tool
      McpSyncClient smsSenderTool // remote tool
  )  {

    this.ai = ai
        .defaultSystem(SYSTEM_PROMPT) // assumed role
        .defaultTools(adoptionSchedulerTool)
        .defaultToolCallbacks(SyncMcpToolCallbackProvider.builder().mcpClients(smsSenderTool).build())
        .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
        .build();
    // TODO 1: add default SYSTEM_PROMPT
    // TODO 3: add Q&A vector store with available dogs
    // TODO 5: add the adoption scheduler local MCP tool + annotate it
    // TODO 6: add the SMS sender remote MCP tool
  }


  @GetMapping(value = "/{username}/assistant", produces = "text/markdown")
  Flux<String> assistant(@PathVariable String username, @RequestParam String q) {
    // TODO 2: return Flux<String> for better UX
    // TODO 3: add chat memory advisor per username
    // TODO 5: add a system prompt with the current username
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

  record DogSearchResultDto(/*TODO */int id, String name, String breed) {}

  @GetMapping(value = "/{username}/search")
  List<DogSearchResultDto> search(@PathVariable String username, @RequestParam String q) {
    // TODO 7: get structured JSON data from AI. tell it in a system prompt.
    //  Hint: .entity(new ParameterizedTypeReference<>() {});
    return ai.prompt()
        .system("Based on the user query, return ONLY a JSON array of objects, NOT an JSON object.")
        .user(q)
        .call()
        .entity(new ParameterizedTypeReference<>() {});
  }
}

