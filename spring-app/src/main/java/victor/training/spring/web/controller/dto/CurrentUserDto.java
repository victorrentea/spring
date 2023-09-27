package victor.training.spring.web.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CurrentUserDto {
   public String username;
   public String role;
   public List<String> authorities;
}
