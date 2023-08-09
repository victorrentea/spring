package victor.training.spring.security.config.header;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RolesToPrivilegesPicnicStyle {

    public Set<String> getPrivileges(String role) {
        switch (role) {
            case "ADMIN": return Set.of("ROLE_TRAINING_DELETE", "ROLE_TRAINING_SEARCH");
            case "USER": return Set.of("ROLE_TRAINING_SEARCH");
            default:
                return Set.of();
        }
    }

}
