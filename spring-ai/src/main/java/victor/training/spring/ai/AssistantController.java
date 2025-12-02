package victor.training.spring.ai;

import io.modelcontextprotocol.client.McpSyncClient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallback;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Data
@Entity
class Dog {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String description;
  private String owner;
  private String vectorId = UUID.randomUUID().toString();
}
interface DogRepo extends JpaRepository<Dog, Integer> {}

@RestController
// From https://www.youtube.com/watch?v=9mOuvrZtLbc&t=2s
class AssistantController {
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

  @GetMapping(value = "/{user}/assistant",produces = "text/markdown")
  String assistant(@PathVariable String user, @RequestParam String question) {
    var advisorForUser = memory.computeIfAbsent(user, k -> newAdvisor());

    return ai.prompt()
        .user(question)
        .advisors(advisorForUser)
        .call()
        .content();
  }

  private PromptChatMemoryAdvisor newAdvisor() {
    return PromptChatMemoryAdvisor
        .builder(MessageWindowChatMemory.builder().chatMemoryRepository(new InMemoryChatMemoryRepository()).build())
        .build();
  }
}

