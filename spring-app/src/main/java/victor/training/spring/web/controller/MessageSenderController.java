package victor.training.spring.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MessageSenderController {
    static int i; // valeu!
    private final StreamBridge streamBridge;

    @GetMapping("api/send-message")
    public String sendMessage() {
        String reason = "Give me money! #" + ++i;
        TransferMessage message = new TransferMessage("IBAN", 100, reason);
        streamBridge.send("paymentRequest-out-0", message);
        return "Sent message: " + message;
    }

    @Value
    public static class TransferMessage {
        String iban;
        int amount;
        String reason;
    }
}
