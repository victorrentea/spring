package victor.training.spring.ai;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
class JobAdoptionScheduler {
  @Tool(description = "send an SMS about the pickup details following scheduling the adoption of a dog")
  String sendPickupDetailsSMS(
      @ToolParam(description = "the id of the dog") int dogId,
      @ToolParam(description = "the name of the dog") String dogName,
      @ToolParam(description = "the username") String username,
      ToolContext toolContext) {
    log.info("Sending SMS with the pickup details of dog id:{} to user:{}", dogId, username);

    try {
      Optional<McpSyncServerExchange> exchangeOpt = McpToolUtils.getMcpExchange(toolContext);
      exchangeOpt.ifPresent(exchange -> {
        if (exchange.getClientCapabilities().sampling() != null) {
          log.info("Client Sampling ON✅");
          String userPrompt = "the user " + username + " adopts dog " + dogName + ", write a funny SMS for pickup up the dog. exclude any further actions proposed back to user - your response should be the final SMS to send";
          McpSchema.CreateMessageResult samplingResponse = exchange.createMessage(McpSchema.CreateMessageRequest.builder()
              .systemPrompt("You are a poet!")
              .messages(List.of(new McpSchema.SamplingMessage(McpSchema.Role.USER, new McpSchema.TextContent(userPrompt))))
              .build());

          var poeticSMS = ((McpSchema.TextContent) samplingResponse.content()).text();
          log.info("poetic SMS: " + poeticSMS);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return "SMS sent to user's phonenumber";
  }
}