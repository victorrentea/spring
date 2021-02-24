package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class PropertiesApp implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

    @Value("${welcome.welcomeMessage}")
    String message;
    @Value("${welcome.host.port}")
    int port;

    @Value("${welcome.supportUrls}")
    List<String> sitesCsv;

    @Value("${welcome.help.helpUrl}")
    URL url;// File Resource Class<?>

    @Override
    public void run(String... args) throws Exception {
        System.out.println("WELLCOME: " + message);
        System.out.println("sitesCsv: " + sitesCsv);
        System.out.println("url: " + url);
    }
}




