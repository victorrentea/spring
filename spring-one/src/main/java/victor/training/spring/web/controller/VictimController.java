package victor.training.spring.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("victim")
public class VictimController {
   @PostMapping("transfer-rest")
   //@CrossOrigin(origins = "*")  or  implement WebMvcConfigurer with @Configuration + cors
   public void transferRest(@RequestBody MoneyTransferRequest request) {
      log.warn("Transferring {} to {}, IBAN = {}", request.getAmount(), request.getRecipientFullName(), request.getRecipientIban());
   }

   @PostMapping("transfer-form")
   @ResponseBody
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