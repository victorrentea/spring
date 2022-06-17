package victor.training.spring.web.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

    public static Optional<UserRole> valueOfOpt(String name) {
        for (UserRole userRole : values()) {
            if (userRole.name().equals(name)) {
                return Optional.of(userRole);
            }
        }
        return Optional.empty();
    }
}
