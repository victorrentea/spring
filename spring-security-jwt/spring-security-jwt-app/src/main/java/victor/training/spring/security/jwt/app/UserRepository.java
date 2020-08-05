package victor.training.spring.security.jwt.app;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private static final Map<String, SecurityUser> dummyData = new HashMap<>();
    static {
        dummyData.put("test", new SecurityUser("test", "Test UserVO", "USER"));
        dummyData.put("admin", new SecurityUser("admin", "Admin UserVO", "ADMIN"));
    }
    public SecurityUser findByUsername(String username) {
        return dummyData.get(username.toLowerCase());
    }
}
