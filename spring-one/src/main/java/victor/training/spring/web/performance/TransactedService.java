package victor.training.spring.web.performance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.Message;
import victor.training.spring.web.repo.MessageRepo;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactedService {
   private final ExpensiveApiClient apiClient;
   private final MessageRepo messageRepo;
   public void flow() {
      String data = apiClient.blockingCall();
      messageRepo.save(new Message(data));
   }
}
