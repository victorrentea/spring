package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@RequiredArgsConstructor
public class TransactionsApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TransactionsApp.class, args);
    }
    private final Playground playground;

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

