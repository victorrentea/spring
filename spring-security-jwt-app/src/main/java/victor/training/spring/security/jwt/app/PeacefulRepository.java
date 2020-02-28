package victor.training.spring.security.jwt.app;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PeacefulRepository {
    public String save() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("INSERT INTO table_bla_bla SET LAST_MODIFIED_BY=" + userName);
        return userName;
    }
}
