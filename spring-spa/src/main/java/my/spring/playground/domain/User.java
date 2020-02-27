package my.spring.playground.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected User() {} // pt dragul de Hibernate

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
