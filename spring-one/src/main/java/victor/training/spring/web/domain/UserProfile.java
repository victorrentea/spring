package victor.training.spring.web.domain;

import java.util.Arrays;
import java.util.List;

public enum UserProfile {
    USER("runSearch"),
    ADMIN("runSearch",
            "deleteCourse");
    public final List<String> permissions;

    UserProfile(String... permissions) {
        this.permissions = Arrays.asList(permissions);
    }
}
