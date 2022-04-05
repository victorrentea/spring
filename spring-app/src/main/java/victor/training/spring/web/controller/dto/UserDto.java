package victor.training.spring.web.controller.dto;

import victor.training.spring.web.entity.User;
import victor.training.spring.web.entity.UserRole;

public class UserDto {
   public Long id;
   public String name;
   public UserRole profile;

   public UserDto() {
   }

   public UserDto(User user) {
      id = user.getId();
      name = user.getName();
      profile = user.getRole();
   }
}
