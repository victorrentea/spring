package victor.training.spring.web.domain;

import javax.persistence.*;
import java.util.*;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    @Enumerated(EnumType.STRING)
    private UserProfile profile;
    @ElementCollection
    private Set<Long> managedTeacherIds = new HashSet<>();
    public User() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, UserProfile profile, List<Long> managedTeacherIds) {
        this.username = username;
        this.profile = profile;
        this.managedTeacherIds = new HashSet<>(managedTeacherIds);
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
}
