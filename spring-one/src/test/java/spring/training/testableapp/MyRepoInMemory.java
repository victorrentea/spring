package spring.training.testableapp;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Primary
@Profile("orderRepoMocked")
public class MyRepoInMemory implements IMyRepo {
    private Map<Long, Order> date = new HashMap<>();
    @Override
    public Order findById(long id) {
        return null;
    }

    public void save(Order order) {
//        date.put(order.getId, order);
    }
}
