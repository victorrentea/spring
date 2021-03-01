package victor.training.spring.web.domain;

import javax.persistence.*;
import java.util.*;

import static java.util.Collections.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserProfile profile;
    @ElementCollection
    private Set<Long> managedTeacherIds = new HashSet<>();
    public User() {
    }
    public User(String username) {
        this(username, UserProfile.USER, emptyList());
    }
    public User(String username, UserProfile profile, List<Long> managedTeacherIds) {
        this.username = username;
        this.name=username;
        this.profile = profile;
        this.managedTeacherIds = new HashSet<>(managedTeacherIds);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public UserProfile getProfile() {
        return profile;
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
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", profile=" + profile +
               ", managedTeacherIds=" + managedTeacherIds +
               '}';
    }
}
