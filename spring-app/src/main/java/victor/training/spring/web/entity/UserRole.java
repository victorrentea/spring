package victor.training.spring.web.entity;

import java.util.*;

public enum UserRole {
    USER("TRAINING_SEARCH", "TRAINING_EDIT"),
    POWER("TRAINING_SEARCH", "UPDATE_TRAINING", "DELETE_TRAINING"),
    ADMIN("SEARCH_TRAINING" ,"UPDATE_TRAINING", "DELETE_TRAINING", "TEACHER_EDIT");
    private final List<String> priviledges;

    UserRole(String... priviledges) {
        this.priviledges = List.of(priviledges);
    }

    public static List<String> expandRoleToPriviledges(List<String> tokenRoles) {
      return tokenRoles.stream()
            // use a local Role enum to expand to fine-grained roles
            .flatMap(role -> valueOfOpt(role).orElseThrow().priviledges.stream())
            .toList();
    }

    public List<String> getSubRoles() {
        return priviledges;
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
