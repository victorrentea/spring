package victor.training.spring.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


@SpringBootTest
public class SiEu {

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    final void before() {
//        applicationContext.getEnvironment().
    }
    @Test
    void explore() {

    }
}
