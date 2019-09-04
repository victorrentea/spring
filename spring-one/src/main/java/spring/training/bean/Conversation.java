package spring.training.bean;

import javax.annotation.PostConstruct;

public class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person one, Person two) {
        this.one = one;
        this.two = two;
    }


    public void start() {
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }
}
