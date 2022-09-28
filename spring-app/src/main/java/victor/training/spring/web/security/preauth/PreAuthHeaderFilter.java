//package victor.training.spring.web.security.preauth;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Map;
//
//public class PreAuthHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
//    public PreAuthHeaderFilter(AuthenticationManager authenticationManager) {
//        setAuthenticationManager(authenticationManager);
//    }
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @Override
//    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//        String username = request.getHeader("X-User");
//        String rolesStr = request.getHeader("X-User-Roles");
//        if (username == null || rolesStr == null) return null;
//        if (username.isBlank() || rolesStr.isBlank()) return null;
//        List<String> roles = List.of(rolesStr.split(","));
//        Map<String, String> attributesMap = Map.of(); // todo read from other headers
//        return new PreAuthHeaderPrincipal(username, roles, attributesMap);
//    }
//
//    @Override
//    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//        return "null";
//    }
//}
