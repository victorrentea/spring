package victor.training.spring.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PropertiesApp {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

    @Autowired
    private WelcomeInfoProps welcomeInfo;

    public void method() {
        System.out.println(welcomeInfo.getWelcomeMessage());
    }
}




