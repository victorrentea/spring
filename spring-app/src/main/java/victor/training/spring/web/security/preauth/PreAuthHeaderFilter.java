package victor.training.spring.web.security.preauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.client.AsyncRestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PreAuthHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {
    public PreAuthHeaderFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);

//        new AsyncRestTemplate().exchange().completable()
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String username = request.getHeader("X-User");
        String rolesStr = request.getHeader("X-User-Roles");
        if (username == null || rolesStr == null) return null;
        if (username.isBlank() || rolesStr.isBlank()) return null;
        List<String> roles = List.of(rolesStr.split(","));
        Map<String, String> attributesMap = Map.of(); // todo read from other headers
        return new PreAuthHeaderPrincipal(username, roles, attributesMap);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "null";
    }
}
