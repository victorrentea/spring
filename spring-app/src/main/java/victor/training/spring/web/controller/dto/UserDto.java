package victor.training.spring.web.controller.dto;

import victor.training.spring.web.entity.UserEntity;
import victor.training.spring.web.entity.UserRole;

public class UserDto {
   public Long id;
   public String name;
   public UserRole profile;

   public UserDto() {
   }

   public UserDto(UserEntity userEntity) {
      id = userEntity.getId();
      name = userEntity.getName();
      profile = userEntity.getRole();
   }
}
