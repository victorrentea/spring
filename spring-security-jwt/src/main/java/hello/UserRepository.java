package hello;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private static final Map<String, UserVO> dummyData = new HashMap<>();
    static {
        dummyData.put("test", new UserVO("test", "Test UserVO", "USER"));
        dummyData.put("admin", new UserVO("admin", "Admin UserVO", "ADMIN"));
    }
    public UserVO findByUsername(String username) {
        return dummyData.get(username.toLowerCase());
    }
}
