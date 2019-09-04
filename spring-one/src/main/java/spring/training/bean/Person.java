package spring.training.bean;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init() {
        System.out.println("new Person " + name);
    }

    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}
