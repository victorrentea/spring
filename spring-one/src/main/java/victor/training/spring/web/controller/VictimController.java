package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("victim")
public class VictimController {
   @PostMapping("transfer-rest")
   //@CrossOrigin(origins = "*")  or  implement WebMvcConfigurer with @Configuration + cors
   public void transfer(@RequestBody MoneyTransferRequest request) {
      log.warn("Transferring {} to {}, IBAN = {}", request.amount, request.recipientFullName, request.recipientIban);
   }
}

class MoneyTransferRequest {
   public String recipientFullName;
   public String recipientIban;
   public int amount;
}