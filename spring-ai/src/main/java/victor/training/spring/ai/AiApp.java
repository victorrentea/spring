package victor.training.spring.ai;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import victor.training.spring.ai.other.VictorTrainingCatalog;
import victor.training.spring.ai.other.WeatherMCP;

@SpringBootApplication
public class AiApp {

  public static void main(String[] args) {
    SpringApplication.run(AiApp.class, args);
  }

  @Bean
  ToolCallbackProvider weatherTools(WeatherMCP weatherMCP, VictorTrainingCatalog victorTrainingCatalog) {
    return MethodToolCallbackProvider.builder().toolObjects(weatherMCP, victorTrainingCatalog).build();
  }

  @Bean
  McpSyncClient mcpSyncClient(SamplingMcpClientHandler samplingMcpClientHandler) {
    String remoteMcpServer = "http://localhost:8081";
    var transport = HttpClientSseClientTransport.builder(remoteMcpServer).build();
    var mcpClient = McpClient.sync(transport)
        .sampling(samplingMcpClientHandler::handleSampling)
        .capabilities(McpSchema.ClientCapabilities.builder().sampling().build())
        .build();
    try {
      mcpClient.initialize();
    }catch (Exception e) {
      throw new RuntimeException("Unable to connect to remote MCP server at: " + remoteMcpServer, e);
    }
    return mcpClient;
  }
}