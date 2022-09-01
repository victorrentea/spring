package victor.training.spring.performance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.entity.Message;
import victor.training.spring.web.repo.MessageRepo;

@Service
@RequiredArgsConstructor
public class TransactedService {
   private final ExpensiveApiClient apiClient;
   private final MessageRepo messageRepo;
   private final OtherService service;

   public void flow() {
      String data = apiClient.blockingCall();
      service.atomic(data);
   }

}

@Slf4j
@RequiredArgsConstructor
@Service
class OtherService {

   private final MessageRepo messageRepo;

   @Transactional
   public void atomic(String data) {
      messageRepo.save(new Message(data));
      messageRepo.save(new Message(data));
   }
}
