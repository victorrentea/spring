package victor.training.spring.props;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class PropApp {
    public static void main(String[] args) {
        SpringApplication.run(PropApp.class, args);
    }

//    @Bean
//    public DataSource dataSource() {
//
//    }
}
