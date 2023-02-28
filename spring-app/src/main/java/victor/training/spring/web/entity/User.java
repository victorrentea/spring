package victor.training.spring.web.entity;

import javax.persistence.*;
import java.util.*;

import static java.util.Collections.*;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ElementCollection
    private Set<Long> managedTeacherIds = new HashSet<>();
    public User() {
    }
    public User(String username) {
        this(username, username, UserRole.USER, emptyList());
    }
    public User(String fullName, String username, UserRole role, List<Long> managedTeacherIds) {
        this.username = username;
        this.name=fullName;
        this.role = role;
        this.managedTeacherIds = new HashSet<>(managedTeacherIds);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public Set<Long> getManagedTeacherIds() {
        return unmodifiableSet(managedTeacherIds);
    }

    public Long getId() {
        return id;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', role=%s, managedTeacherIds=%s}", id, username, role, managedTeacherIds);
    }
}
