package victor.training.spring.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageSenderController {
    private final StreamBridge streamBridge;

    @GetMapping("api/send-message")
    public String sendMessage() {
        streamBridge.send("paymentRequest-out-0", "Give me money!");
        return "Sent!";
    }


}
