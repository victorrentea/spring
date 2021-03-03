package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;

@Component
@RequiredArgsConstructor
public class KafkaClient {

   public void logSearch(TrainingSearchCriteria criteria) {
      // apel SOAP client
      // apel RMI catre altu
      throw new IllegalArgumentException("Din teste nu poti s-o chemi");
   }
}
