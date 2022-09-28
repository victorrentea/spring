package victor.training.spring.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
@Secured("ADMIN") // it propagates on al the methods; same as @Transactional, @Timed,
public class AdminController {
    @GetMapping("/putin")
    public String toLaunchOrNotToLaunch() {
        return "no";
    }
}
