package victor.training.spring.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
}
