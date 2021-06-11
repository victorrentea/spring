package victor.training.spring.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
//   @CrossOrigin(origins = "http://api.cofacelocalhost:8080")

@RequestMapping("victim")
public class VictimController {
//   @CrossOrigin(origins = "http://127.0.0.1:9999") //
   @PostMapping("transfer-rest")
//   @CrossOrigin(origins = "http://127.0.0.1:9999",allowCredentials = "true")  //or  implement WebMvcConfigurer with @Configuration + cors
   public void transferRest(@RequestBody MoneyTransferRequest request) {
      log.warn("Transferring {} to {}, IBAN = {}", request.getAmount(), request.getRecipientFullName(), request.getRecipientIban());
   }

//   @CrossOrigin(origins = "http://localhost:8080")
   @PostMapping("transfer-form")
   public String transferForm(@ModelAttribute MoneyTransferRequest request) {
      log.warn("Transferring {} to {}, IBAN = {}", request.getAmount(), request.getRecipientFullName(), request.getRecipientIban());
      return "Done";
   }
}

@Data
class MoneyTransferRequest {
   private String recipientFullName;
   private String recipientIban;
   private int amount;
}