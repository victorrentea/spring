package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Async
@SpringBootApplication
@EnableJpaRepositories
@RequiredArgsConstructor
@EnableAspectJAutoProxy(exposeProxy = true)
public class TransactionsApp  {
    public static void main(String[] args) {
        SpringApplication.run(TransactionsApp.class, args);
    }

    @Bean
    public ThreadPoolTaskExecutor throttledTo20() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("long-");
        executor.initialize();
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Bean
    public TransactionTemplate newTx(PlatformTransactionManager transactionManager) {
        TransactionTemplate tx = new TransactionTemplate(transactionManager);
        tx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return tx;
    }
}

@Service
@RequiredArgsConstructor
class Cycles implements CommandLineRunner{
    private final Playground playground;

//    @PostConstruct
//    @Transactional
//    public void method() {
//
//    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("What did Spring injected me ? " + playground.getClass());
        System.out.println("============= TRANSACTION ONE ==============");
        playground.transactionOne();
        System.out.println("============= TRANSACTION ONE bis ==============");
//        playground.transactionOneBis();
        System.out.println("============= TRANSACTION TWO ==============");
        playground.untransacted();
        System.out.println("============= END ==============");
    }

}

