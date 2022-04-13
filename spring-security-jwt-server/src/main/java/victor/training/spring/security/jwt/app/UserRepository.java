package victor.training.spring.security.jwt.app;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private static final Map<String, SecurityUser> dummyData = new HashMap<>();
    static {
        dummyData.put("test", new SecurityUser("test", "Test UserVO", "USER"));
        dummyData.put("admin", new SecurityUser("admin", "Admin UserVO", "ADMIN"));
//        Connector c;
//        SSLHostConfig s;
//        s.set
//        c.addSslHostConfig(s);
    }
    public SecurityUser findByUsername(String username) {
        return dummyData.get(username.toLowerCase());
    }
}
