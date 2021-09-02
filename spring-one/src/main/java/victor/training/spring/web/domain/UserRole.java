package victor.training.spring.web.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserRole {
    USER("training.search", "training.edit"),
    ADMIN("training.search" ,"training.edit", "training.delete", "teacher.edit");
    private final Set<String> priviledges;

    UserRole(String... priviledges) {
        this.priviledges = new HashSet<>(Arrays.asList(priviledges));
    }

    public Set<String> getPriviledges() {
        return priviledges;
    }
}
