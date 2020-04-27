package di2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class App2 implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App2.class, args);
    }
    @Autowired
    IConfig i;

    @Autowired
    ISmsSender smsSender;
    @Override
    public void run(String... args) throws Exception {
        System.out.println(i.getClass());
        System.out.println(i.load());
    }
}
interface IConfig {
    String load();
}
@Primary
@Profile("!local")// daca printre profilele active se gaseste si "local"
// atunci acest bean devine 'invizibil' pt Spring
@Component
class ConfigDinBaza implements IConfig {
    @Override
    public String load() {
        return "din baza";
    }//pt prod
}
@Component
class ConfigDinFisier implements IConfig {
    @Override
    public String load() {
        return "din fisier";
    } // pe local, si poate si pe primul mediu Linux
}

interface ISmsSender {
    void sendSms();
}
@Service
class SmsSender implements ISmsSender {
    @Override
    public void sendSms() {
        // din teste:
        throw new IllegalArgumentException("N-o chema ca-i pe bani");
    }
}