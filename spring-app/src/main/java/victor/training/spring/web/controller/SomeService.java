package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class SomeService {
//    @SneakyThrows
//    public String deep() {
//        return Executors.newFixedThreadPool(1)
//                .submit(() -> deepereven())
//                .get();
//    }
    @Async
    public CompletableFuture<String> deep() {
        return CompletableFuture.completedFuture(deepereven());
    }

    private String deepereven() {
        return deeper();
    }

//    @Scheduled(fixedRate = 1000)
    public String deeper() {
        log.debug("Where am I ?");
        String currentlyLoggedInUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("UPDATE .. WHERE SET LAST_MODIFIED_BY="+currentlyLoggedInUsername);


        return currentlyLoggedInUsername;
    }
}
