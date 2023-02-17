package victor.training.spring.web.sandbox;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Slf4j
@RestController
@RequestMapping("api/victim")
public class VictimController {
   @PostMapping("transfer-rest")
//    @CrossOrigin(originPatterns = {"http://*"},allowCredentials = "true") // OMG!
//    @CrossOrigin(originPatterns = {"http://localhost:8081"},allowCredentials = "true") // only for NODE
   public void transferRest(@RequestBody MoneyTransferRequest request) {
      log.warn("Transferring {} to {}, IBAN = {}", request.getAmount(), request.getRecipientFullName(), request.getRecipientIban());
   }

   @PostMapping("transfer-form")
   public String transferForm(@ModelAttribute MoneyTransferRequest request) {
      log.warn("TransferringPOST {} to {}, IBAN = {}", request.getAmount(), request.getRecipientFullName(), request.getRecipientIban());
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

   @PostMapping("upload")
   public String upload(@RequestParam String fileName, @RequestParam MultipartFile file) throws IOException {
      log.debug("Uploading file name={} size={}", fileName, file.getSize());
      File targetFile = new File("in/", fileName);
      System.out.println("Writing the file to " + targetFile.getAbsolutePath());
      try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
         IOUtils.copy(file.getInputStream(), outputStream);
      }
      return "DONE";
   }


   @Autowired
   private JdbcTemplate jdbc;
   @GetMapping("sql-injection")
   public String sqlInjection(@RequestParam String name) throws IOException {
      Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM MESSAGE WHERE MESSAGE='" + name + "'", Integer.class);
      // TODO think ORDER BY
      return "DONE; Found = " + n;
   }

   @GetMapping(value = "fetch-image",produces = "image/jpeg")
   public byte[] fetchImage(@RequestParam String url) throws IOException {
      log.info("Retrieving url: {}", url);
      // stage 1 : allows access to file:///c:/...
      return IOUtils.toByteArray(new URL(url).openStream());

      // stage2 : blocks file accesses
//      RestTemplate rest = new RestTemplate();
//      return rest.getForObject(url, byte[].class);

      // stage3: pattern match: still allows port scanning
//      if (!url.endsWith(".jpg")) {
//         throw new IllegalArgumentException("Should end in .jpg");
//      }
   }
}

@Data
class MoneyTransferRequest {
   private String recipientFullName;
   private String recipientIban;
   private int amount;
}