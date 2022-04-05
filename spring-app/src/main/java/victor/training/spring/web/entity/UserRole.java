package victor.training.spring.web.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserRole {
    USER("training.search", "training.edit"),
    ADMIN("training.search" ,"training.edit", "training.delete", "teacher.edit");
    private final Set<String> authorities;

    UserRole(String... authorities) {
        this.authorities = new HashSet<>(Arrays.asList(authorities));
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
}
