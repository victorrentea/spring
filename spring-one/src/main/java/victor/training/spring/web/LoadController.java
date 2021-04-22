package victor.training.spring.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoadController {
   private final TransactedService transactedService;

   @GetMapping("load/conn-starve")
   public void starveConnections() {
       transactedService.flow();
   }

}


