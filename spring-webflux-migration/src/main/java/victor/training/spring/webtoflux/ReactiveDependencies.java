package victor.training.spring.webtoflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
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
    return ReactiveSecurityContextHolder.getContext()
            .doOnNext(securityContext -> log.info("🐇 send message: {} as {}", message, securityContext.getAuthentication().getName()))
            .then();
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
    // legacy library that DOES BLOCK inside it, that I can't change

    // whenever you see a method returning Mono/Flux, that method is NOT ALLOWED TO :
    // - block
    // - throw exception!
//    return Mono.error(new IllegalArgumentException());

//    return Mono.just(  Lib.blockingCall(data)   ); // BAD because blockingCall might block thread or throw exceptions
    return Mono.fromCallable(() -> Lib.blockingCall(data))
            .subscribeOn(Schedulers.boundedElastic()); // the only place you are allowed to block threads in reactor
  }

}
