package victor.training.spring.performance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoadMonitoringController {
  private final ExpensiveApiClient apiClient;
  private final TransactedService transactedService;

  @GetMapping("load/expensive")
  public CompletableFuture<String> expensive() {
    return apiClient.asyncCall();
  }

  @GetMapping("load/conn-starve")
  public void starveConnections() {
    transactedService.flow();
  }

}


