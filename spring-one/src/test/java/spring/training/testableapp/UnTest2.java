package spring.training.testableapp;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnTest2 {
    @Autowired
    private MyController controller;

    @Test
    public void dummy() {
        System.out.println("test3");
        LocalDate actual = controller.getStuff();

        Assertions.assertThat(actual).isEqualTo(now().minusDays(1));
    }
}


