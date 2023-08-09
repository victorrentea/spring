package victor.training.spring.security.config.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PreAuthHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
    public PreAuthHeaderFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String username = request.getHeader("x-user");
        String rolesStr = request.getHeader("x-user-role");
        if (username == null || rolesStr == null || username.isBlank() || rolesStr.isBlank()) {
            log.error("'x-user' and 'x-user-roles' NOT found in request headers");
            return null;
        }
        List<String> roles = List.of(rolesStr.split(","));

        List<String> privileges = roles.stream()
                .flatMap(role -> rolesToPrivilegesPicnicStyle.getPrivileges(role).stream())
                .collect(Collectors.toList());
        log.info("Priviledges: " + privileges);

        return new PreAuthHeaderPrincipal(username, privileges);
    }

    @Autowired
    private RolesToPrivilegesPicnicStyle rolesToPrivilegesPicnicStyle ;

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "null";
    }
}
