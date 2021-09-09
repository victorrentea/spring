package victor.training.spring.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/victim")
public class VictimController {
   @PostMapping("transfer-rest")
//    @CrossOrigin(origins = "http://localhost:9999",allowCredentials = "true")
//   @CrossOrigin(origins = "*") // api probabil unsecured deschis oricarei alte aplicatii angulare
   public void transferRest(@RequestBody MoneyTransferRequest request) {
      log.warn("Transferring {} to {}, IBAN = {}", request.getAmount(), request.getRecipientFullName(), request.getRecipientIban());
   }

   @PostMapping("transfer-form")
   public String transferForm(@ModelAttribute MoneyTransferRequest request) {
      log.warn("Transferring {} to {}, IBAN = {}", request.getAmount(), request.getRecipientFullName(), request.getRecipientIban());
      return "Done";
   }
   @GetMapping("transfer-over-get-BAD")
   public String transferForm(
       @RequestParam("recipientName") String recipientName,
       @RequestParam("recipientIBAN") String recipientIban,
       @RequestParam("amount") String amount) {
      log.warn("TransferringGET {} to {}, IBAN = {}", amount, recipientName, recipientIban);
      return "Done";
   }
}

@Data
class MoneyTransferRequest {
   private String recipientFullName;
   private String recipientIban;
   private int amount;
}