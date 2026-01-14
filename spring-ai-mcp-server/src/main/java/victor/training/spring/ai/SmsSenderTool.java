package victor.training.spring.ai;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CreateMessageRequest;
import io.modelcontextprotocol.spec.McpSchema.CreateMessageResult;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
class SmsSenderTool {
  @Tool(description = "send an SMS about the pickup details following scheduling the adoption of a dog")
  String sendPickupDetailsSMS(
      @ToolParam(description = "the id of the dog") int dogId,
      @ToolParam(description = "the name of the dog") String dogName,
      @ToolParam(description = "the username") String username,
      ToolContext toolContext) {
    log.info("Sending SMS with the pickup details of dog id:{} to user:{}", dogId, username);

    var sms = McpToolUtils.getMcpExchange(toolContext)
        .map(exchange -> generatePoeticSMS(dogName, username, exchange))
        .orElse("Standard SMS: Your dog '%s' is ready for pickup!".formatted(dogName));

    log.info("SMS sent to user: {}", sms);

    return "SMS sent to user's phone number";
  }

  private String generatePoeticSMS(String dogName, String username, McpSyncServerExchange exchange) {
    try {
      if (exchange.getClientCapabilities().sampling() == null) {
        log.info("Sampling OFF❌");
        return null;
      }
      log.info("Sampling ON✅");
      String userPrompt = "the user " + username + " adopts dog " + dogName + ", write a funny SMS for pickup up the dog. exclude any further actions proposed back to user - your response should be the final SMS to send";
      CreateMessageRequest samplingRequest = CreateMessageRequest.builder()
          .systemPrompt("You are a poet!")
          .messages(List.of(new McpSchema.SamplingMessage(McpSchema.Role.USER, new TextContent(userPrompt))))
          .build();
      CreateMessageResult samplingResponse = exchange.createMessage(samplingRequest);

      TextContent textContent = (TextContent) samplingResponse.content();
      return textContent.text();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}