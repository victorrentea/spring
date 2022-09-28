package victor.training.spring.web.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "USERS")
public class User {
    @Id
    private Long id;
    private String username;
    private String name;
    private UserRole role;
    public User() {
    }
    public User(String username) {
        this(username, username, UserRole.USER);
    }
    public User(String fullName, String username, UserRole role) {
        this.username = username;
        this.name=fullName;
        this.role = role;
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

    public Long getId() {
        return id;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", role=" + role +
               '}';
    }
}
