package victor.training.spring.web.controller.dto;

import victor.training.spring.web.security.SecurityUser;

import java.util.List;
import java.util.Set;

public class LoggedInUserDto {
   public String username;
   public Set<String> permissions;
   public LoggedInUserDto(SecurityUser user) {
      username= user.getUsername();
      permissions = user.getPermissions();
   }
}

