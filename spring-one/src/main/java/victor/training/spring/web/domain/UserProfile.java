package victor.training.spring.web.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserProfile {
    USER("runSearch"),
    ADMIN("runSearch", "deleteTraining");
    public final Set<String> authorities;

    UserProfile(String... authorities) {
        this.authorities = new HashSet<>(Arrays.asList(authorities));
    }


}
