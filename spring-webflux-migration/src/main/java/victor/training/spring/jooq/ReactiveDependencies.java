package victor.training.spring.jooq;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class ReactiveDependencies {


  public Mono<String> mongoRepoSave(String document) {
    return Mono.fromRunnable(() -> log.info("Persist the document: {}", document))
            .thenReturn(document);
  }

  public Mono<Void> rabbitSend(String message) {
    return Mono.fromRunnable(() -> log.info("🐇 send message: {}", message));
  }

  public Mono<ReaderProfile> fetchUserProfile(Long readerId) {
    return Mono.fromRunnable(() -> log.info("Rest call PROFILE: {}", readerId))
            .thenReturn(new ReaderProfile());
  }
  public Mono<Boolean> checkAddress(ReaderProfile readerProfile) {
    return Mono.fromRunnable(() -> log.info("Rest call ADDRESS: {}", readerProfile))
            .thenReturn(true);
  }
  public Mono<Boolean> checkPhone(ReaderProfile readerProfile) {
    return Mono.fromRunnable(() -> log.info("Rest call PHONE: {}", readerProfile))
            .thenReturn(true);
  }

  public Mono<String> wsdlCall(String data) {
    return Mono.fromCallable(() -> Lib.blockingCall(data) )
            .subscribeOn(Schedulers.boundedElastic()); // in that scheduler (~= thread pool) you have enough space to block eg 120 thread
  }

}
