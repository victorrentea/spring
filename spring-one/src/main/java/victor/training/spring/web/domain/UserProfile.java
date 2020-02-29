package victor.training.spring.web.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum UserProfile {
    USER("runSearch"),
    ADMIN("runSearch",
            "deleteCourse");
    public final Set<String> permissions;

    UserProfile(String... permissions) {
        this.permissions = new HashSet<>(Arrays.asList(permissions));
    }
}
