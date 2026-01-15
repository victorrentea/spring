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

  AssistantController(
      ChatClient.Builder ai
  )  {

    this.ai = ai
        .defaultSystem(SYSTEM_PROMPT)
        .build();
    // TODO 1: add default SYSTEM_PROMPT
    // TODO 3: add Q&A advisor on the VectorStore with available dogs
    // TODO 5: add the adoption scheduler local MCP tool + annotate it
    // TODO 6: add the SMS sender remote MCP tool via SyncMcpToolCallbackProvider
  }

  @GetMapping(value = "/{username}/assistant", produces = "text/markdown")
  String assistant(@PathVariable String username, @RequestParam String q) {
    // TODO 2: return Flux<String> for better UX
    // TODO 3: add chat memory advisor per username
    // TODO 5: add a system prompt with the current username

    return ai.prompt()
        .user(q)
        .call()
        .content();
  }

  private PromptChatMemoryAdvisor memoryAdvisor() {
    return PromptChatMemoryAdvisor.builder(
        MessageWindowChatMemory.builder()
            .chatMemoryRepository(new InMemoryChatMemoryRepository())
            .build())
        .build();
  }

  record DogSearchResultDto(/*TODO */) {}

  @GetMapping(value = "/{username}/search")
  List<DogSearchResultDto> search(@PathVariable String username, @RequestParam String q) {
    // TODO 7: get structured JSON data from AI. tell it in a system prompt.
    //  then .entity(new ParameterizedTypeReference<>() {});
    return List.of();
  }
}

