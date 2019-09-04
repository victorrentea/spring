package spring.training.testableapp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;

@SpringBootApplication
public class TestableApp {
    public static void main(String[] args) {
        SpringApplication.run(TestableApp.class, args);
    }

}


@Controller
@RequiredArgsConstructor
class MyController {
    private final MyService service;
    // @GetMapping
    public LocalDate getStuff() {
        return service.m();
    }
}

@Service
@RequiredArgsConstructor
class MyService {
    private final MyRepo repo;
    public LocalDate m() {
        Order order = repo.findById(1L);
        return order.getCreationDate();
    }
}

@Repository
class MyRepo {
    public Order findById(long l) {
        throw new NotImplementedException();
    }
}

class Order {
    private LocalDate creationDate;

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}