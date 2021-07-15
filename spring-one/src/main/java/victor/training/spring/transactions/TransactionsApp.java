package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@RequiredArgsConstructor
public class TransactionsApp implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TransactionsApp.class).profiles("spa").run(args);
    }
    private final Playground playground;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("============= TRANSACTION ONE ==============");
        playground.transactionOne();
        System.out.println("============= TRANSACTION TWO ==============");
        try {
            playground.transactionTwo();
        } catch (Exception e) {
            // shaworma
        }
        System.out.println("============= END ==============");
    }
}

