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
public class UnTestFaraNumar {
    @Autowired
    private MyController controller;

    @MockBean // suprascrie beanul MyRepo din context cu un Mockito mock.
    private MyRepo repo;

    @Test
    public void dummy() {
        System.out.println("Test fara numar");
        Order order = new Order();
        order.setCreationDate(now());
        when(repo.findById(anyLong())).thenReturn(order);
        LocalDate actual = controller.getStuff();

        Assertions.assertThat(actual).isEqualTo(now());
    }
}


