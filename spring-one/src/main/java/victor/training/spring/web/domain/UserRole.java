package victor.training.spring.web.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserRole {
    USER("search"),
    ADMIN("search", "training.delete", "training.edit");
    private final Set<String> authorities;

    UserRole(String... authorities) {
        this.authorities = new HashSet<>(Arrays.asList(authorities));
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
}
