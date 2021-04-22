package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
@EnableJpaRepositories
@RequiredArgsConstructor
public class TransactionsApp  {
    public static void main(String[] args) {
        SpringApplication.run(TransactionsApp.class, args);
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
        System.out.println("============= TRANSACTION TWO ==============");
        playground. transactionTwo();
        System.out.println("============= END ==============");
    }

}

