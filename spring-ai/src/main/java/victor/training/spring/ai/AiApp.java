package victor.training.spring.ai;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Inspired from https://www.youtube.com/watch?v=9mOuvrZtLbc&t=2s
@SpringBootApplication
public class AiApp {

  public static final String SYSTEM_PROMPT = """
      You are an AI powered assistant to help people adopt a dog from the adoption
      agency named Pooch Palace. Information about the dogs available
      will be presented below. If there is no information, then return a polite response suggesting we
      don’t have any dogs available. To adopt a dog, the user must be sent an SMS with the details about the pickup.
      When uncertain, ask for clarifications rather than making assumptions.
      """;

  public static void main(String[] args) {
    SpringApplication.run(AiApp.class, args);
  }

  @Bean
  McpSyncClient smsSenderTool(SamplingMcpClientHandler samplingMcpClientHandler) {
    String remoteMcpServer = "http://localhost:8081";
    var transport = HttpClientSseClientTransport.builder(remoteMcpServer).build();
    var mcpClient = McpClient.sync(transport)
//        .capabilities(McpSchema.ClientCapabilities.builder().sampling().build())
//        .sampling(samplingMcpClientHandler::handleSampling)
        .build();
    try {
      mcpClient.initialize();
    }catch (Exception e) {
      throw new RuntimeException("Unable to connect to remote MCP server at: " + remoteMcpServer, e);
    }
    return mcpClient;
  }
}