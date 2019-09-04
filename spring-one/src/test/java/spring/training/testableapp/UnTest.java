package spring.training.testableapp;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import spring.training.ThreadUtils;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnTest {
    @Autowired
    private MyController controller;

    @MockBean // suprascrie beanul MyRepo din context cu un Mockito mock.
    private MyRepo repo;

    @Test
    public void dummy() {
        Order order = new Order();
        order.setCreationDate(now());
        when(repo.findById(anyLong())).thenReturn(order);
        LocalDate actual = controller.getStuff();

        Assertions.assertThat(actual).isEqualTo(now());
    }
}
