package victor.training.spring.ai;

import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CreateMessageRequest;
import io.modelcontextprotocol.spec.McpSchema.CreateMessageResult;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class SamplingMcpClientHandler {
  private final ChatModel chatModel;

  public CreateMessageResult handleSampling(CreateMessageRequest request) {
    List<Message> messages = request.messages().stream()
        .map(msg -> {
          TextContent textContent = (TextContent) msg.content();
          if (msg.role() == McpSchema.Role.USER) {
            return new UserMessage(textContent.text());
          } else {
            return (Message) AssistantMessage.builder().content(textContent.text()).build();
          }
        })
        .toList();

    ChatResponse chatResponse = chatModel.call(new Prompt(messages));

    return CreateMessageResult.builder()
        .role(McpSchema.Role.ASSISTANT)
        .content(new TextContent(chatResponse.getResult().getOutput().getText()))
//        .model(request.modelPreferences().hints().get(0).name())
        .build();
  }
}
