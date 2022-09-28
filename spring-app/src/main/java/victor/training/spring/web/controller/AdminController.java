package victor.training.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @GetMapping("api/admin/putin")
    public String toLaunchOrNotToLaunch() {
        return "no";
    }
}
