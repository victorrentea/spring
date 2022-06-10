package victor.training.spring.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("admin")
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @PostMapping("putin")
    public void fireRockets() {

    }
}
