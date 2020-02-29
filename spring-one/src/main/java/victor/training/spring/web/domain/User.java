package victor.training.spring.web.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private UserProfile profile;
    @ElementCollection
    private List<Long> managedTeacherIds = new ArrayList<>();
    protected User() {
    }
    public User(String username, UserProfile profile, List<Long> managedTeacherIds) {
        this.username = username;
        this.profile = profile;
        this.managedTeacherIds = managedTeacherIds;
    }

    public String getUsername() {
        return username;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public List<Long> getManagedTeacherIds() {
        return unmodifiableList(managedTeacherIds);
    }

    public Long getId() {
        return id;
    }
}
