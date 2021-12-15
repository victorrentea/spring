package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@MapperScan("victor.training.spring.transactions")

@SpringBootApplication
@EnableJpaRepositories
@RequiredArgsConstructor
public class TransactionsApp implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TransactionsApp.class).profiles("spa").run(args);
    }
    private final Playground playground;
    private final Playground2 playground2;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("============= TRANSACTION ONE ==============");
        playground.transactionOne();
        System.out.println("============= TRANSACTION TWO ==============");
        playground.transactionTwo();
        System.out.println("============= PLAY2 ==============");
        playground2.method();
//        playground2.saveError(); // ar merge @Transactional
        System.out.println("============= END ==============");
    }

@Bean
public TransactionTemplate newTx(PlatformTransactionManager transactionManager) {
    TransactionTemplate newTx = new TransactionTemplate(transactionManager);
    newTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    return newTx;
}
//    @Bean
//    public DataSource oraDS() {
//    }
//    @Bean
//    public PlatformTransactionManager oraTM() {
//        PlatformTransactionManager tx = new PlatformTransactionManager();
//        tx.set
//    }
}

