package victor.training.spring.security.jwt.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/ping")
    public String rest() {
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Peace on you " + user.getUsername() + " from " + user.getCountry();
    }

    @GetMapping("/load-user-data")
    public String loadUserData() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username).getFullName();
    }

    @GetMapping("unsecured")
    public String unsecured() {
        return "Unsecured (public) data";
    }
}

