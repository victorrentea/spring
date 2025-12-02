package victor.training.spring.ai;

import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpSampling;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class Sampl {
  private final ChatModel chatModel;

  @McpSampling(clients = "server1")
  public McpSchema.CreateMessageResult handleSampling(McpSchema.CreateMessageRequest request) {
    // Use Spring AI ChatModel for sampling
    List<Message> messages = request.messages().stream()
        .map(msg -> {
          if (msg.role() == McpSchema.Role.USER) {
            return new UserMessage(((McpSchema.TextContent) msg.content()).text());
          } else {
            return (Message) AssistantMessage.builder()
                .content(((McpSchema.TextContent) msg.content()).text())
                .build();
          }
        })
        .toList();

    ChatResponse response = chatModel.call(new Prompt(messages));

    return McpSchema.CreateMessageResult.builder()
        .role(McpSchema.Role.ASSISTANT)
        .content(new McpSchema.TextContent(response.getResult().getOutput().getText()))
//        .model(request.modelPreferences().hints().get(0).name())
        .build();
  }
}
