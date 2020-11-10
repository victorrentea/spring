package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("============= TRANSACTION ONE ==============");
//        playground.transactionOne();
        System.out.println("============= TRANSACTION TWO ==============");
//        playground.transactionTwo();
        System.out.println("============= END ==============");
//        playground.test1();
//        playground.test2();

//        String s = new TranzactiiSiExceptii(null).method();
//        System.out.println(s.toUpperCase());

        exceptii.method();
    }
    @Autowired
    private TranzactiiSiExceptii exceptii;
    // Avantaje:
    // 1) mai testatbil
    // 2) pot sa proxiez. merge magia springului: @Cacheable, @Transaction, @PreAuthorized
    // 3) Nu-mi mai bat eu capul s-o creez pe aia, mai ales daca aia mai avea nevoie si ea de 4-5 dependinte.
}

//class ServDeBiz {
//    @Autowired
//    private TranzactiiSiExceptii exceptii; // mai testatbil
//    public String method() {
//        String s = new TranzactiiSiExceptii().method();
//       return s.toUpperCase();
//    }
//
//}