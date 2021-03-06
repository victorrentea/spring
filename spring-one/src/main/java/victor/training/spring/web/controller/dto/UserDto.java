package victor.training.spring.web.controller.dto;

import victor.training.spring.web.domain.User;
import victor.training.spring.web.domain.UserProfile;

public class UserDto {
   public Long id;
   public String name;
   public UserProfile profile;

   public UserDto() {
   }

   public UserDto(User user) {
      id = user.getId();
      name = user.getName();
      profile = user.getProfile();
   }
}
