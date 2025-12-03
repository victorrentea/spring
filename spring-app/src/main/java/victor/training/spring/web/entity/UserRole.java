package victor.training.spring.web.entity;

import java.util.*;

public enum UserRole {
    USER( //<-- cand te logezi/JWT token vii cu rol din asta
        // la login app expandeaza rolul 'USER' intr-o lista de actiuni/authorities permise:
        "TRAINING_SEARCH", // app-local
        "TRAINING_EDIT"),
    // varianta, pentru guvernanta centralizata de un sef paranoic, poti veni in JWT
    // cu 2MB de json cu o lista huge de actiuni permise / aplicatie

    POWER(
        "TRAINING_SEARCH",
        "TRAINING_EDIT",
        "TRAINING_DELETE"),
    ADMIN("TRAINING_SEARCH" ,"TRAINING_EDIT", "TRAINING_DELETE", "TEACHER_EDIT");
    private final List<String> authorities;

    UserRole(String... subRoles) {
        this.authorities = List.of(subRoles);
    }

    public static List<String> expandToSubRoles(List<String> tokenRoles) {
      return tokenRoles.stream()
            // use a local Role enum to expand to fine-grained roles
            .flatMap(role -> valueOfOpt(role).orElseThrow().authorities.stream())
            .toList();
    }

    public List<String> getSubRoles() {
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
