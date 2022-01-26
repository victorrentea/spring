package victor.training.spring.web.controller.dto;

import java.util.List;

public class LoggedInUserDto {
   public String username;
   public String role;
   public List<String> authorities;
   public String accessLevel;
}
