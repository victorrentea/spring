//package victor.training.spring.jooq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Component
//public class ReactiveDependencies {
//  public Mono<String> mongoRepoSave(String document) {
//    return Mono.fromRunnable(() -> log.info("Persist the document: {}", document))
//            .thenReturn(document);
//  }
//
//  public Mono<Void> rabbitSend(String message) {
//    return Mono.fromRunnable(() -> log.info("🐇 send message: {}", message));
//  }
//
//  public Mono<ReaderProfile> fetchUserProfile(String readerId) {
//    return Mono.fromRunnable(() -> log.info("Rest call PROFILE: {}", readerId))
//            .thenReturn(new ReaderProfile());
//  }
//  public Mono<Boolean> checkAddress(ReaderProfile readerProfile) {
//    return Mono.fromRunnable(() -> log.info("Rest call ADDRESS: {}", readerProfile))
//            .thenReturn(true);
//  }
//  public Mono<Boolean> checkPhone(ReaderProfile readerProfile) {
//    return Mono.fromRunnable(() -> log.info("Rest call PHONE: {}", readerProfile))
//            .thenReturn(true);
//  }
//}
