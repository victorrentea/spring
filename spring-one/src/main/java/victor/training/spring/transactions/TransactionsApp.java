package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
        System.out.println("============= END ==============");
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

