package spring.training.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

public class Person {
    private final String name;
    @Autowired
    private Maternitate maternitate;


    public Person(String name) {
        this.name = name;
        // this.maternitate e null aici
    }

    @PostConstruct
    public void init() {
        System.out.println("new Person " + name);
        // this.maternitate e INJECTAT deja
    }

    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}
@Service
class Maternitate {

}
