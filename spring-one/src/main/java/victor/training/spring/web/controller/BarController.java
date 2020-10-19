package victor.training.spring.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.Barman;
import victor.training.spring.async.DillyDilly;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping
public class BarController {
   private static final Logger log = LoggerFactory.getLogger(BarController.class);
   private final Barman barman;

   public BarController(Barman barman) {
      this.barman = barman;
   }

   @GetMapping("bar")
   public CompletableFuture<DillyDilly> getDilly() {
      log.info("in HTTP thread");
      return barman.getOneBeer()
          .thenCombine(barman.getOneVodka(), DillyDilly::new)
          ;
   }
   @GetMapping("bar2")
   public String getDilly22() {
      return "A";
   }
}
