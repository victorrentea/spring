package victor.training.spring.web.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserRole {
    USER("training.search", "training.edit"),
    ADMIN("training.search" ,"training.edit", "training.delete", "teacher.edit"),
    POWER("training.delete");
    private final Set<String> features;

    UserRole(String... features) {
        this.features = new HashSet<>(Arrays.asList(features));
    }

    public Set<String> getFeatures() {
        return features;
    }
}
