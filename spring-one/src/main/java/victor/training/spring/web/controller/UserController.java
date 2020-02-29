package victor.training.spring.web.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/user")
public class UserController {
	@GetMapping("current")
	public String getCurrentUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return "";
		}
		return "User: " + context.getAuthentication().getName();
	}
}
