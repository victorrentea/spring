package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;
import victor.training.spring.web.service.TrainingService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
  private final TrainingService trainingService;

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUser() {
    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    dto.username = trainingService.altaMetoda();
    dto.authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .map(Object::toString)
            .toList();

    // JWT ("giot") venit pe request header poti obtine claimuri, de ex emailul userului
    // uite aici exemplu schitat comentat:
    
    // if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken jwtAuth) {
    //   String email = jwtAuth.getToken().getClaim("email");
    //   dto.email = email;
    // }
    return dto;
  }

}
