package victor.training.spring.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.util.List;

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
//      File targetFile = new File("in/", fileName);
      File targetFile = Files.createTempFile("data", "dat").toFile();
      System.out.println("Writing the file to " + targetFile.getAbsolutePath());

//      byte[] oom = IOUtils.toByteArray(file.getInputStream());

      try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
         IOUtils.copy(file.getInputStream(), outputStream);
      }
      return "DONE";
   }


   @Autowired
   private JdbcTemplate jdbc;

   public enum MessageColumn {
      MESSAGE, ID
   }

   EntityManager em;

   @GetMapping("sql-injection")
   public String sqlInjection(@RequestParam MessageColumn name) throws IOException {
//      if (!List.of("MESSAGE", "ID").contains(name.toUpperCase())) {
//         throw new IllegalArgumentException();
//      }
      List<Integer> ids = jdbc.queryForList("SELECT id FROM MESSAGE ORDER BY " + name , Integer.class);
      System.out.println(ids);

//      em.createNativeQuery("SELECT id FROM MESSAGE ORDER BY " + name)

      Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM MESSAGE WHERE MESSAGE=?", Integer.class, name);
      // TODO think ORDER BY
      //let's have fun: ' OR 1=1 ; drop table message; select 1 from dual where 's' = 's


      // expect to find SQL injection in codebases in which you find "PreparedStatement" directly used
//      PreparedStatement ps;
//      ps.setI
      return "DONE; Found = " + n;
   }

   @GetMapping(value = "fetch-image",produces = "image/jpeg")
   public byte[] fetchImage(@RequestParam String url) throws IOException {
      log.info("Retrieving url: {}", url);
      // stage 1 : allows access to file:///c:/...
//      return IOUtils.toByteArray(new URL(url).openStream());

      // stage2 : blocks file accesses
      RestTemplate rest = new RestTemplate();
      return rest.getForObject(url, byte[].class);

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