package victor.training.spring.web.security.jwt;

import lombok.Data;
import victor.training.spring.web.entity.UserRole;

import java.io.Serializable;

@Data
public class JwtToken implements Serializable {
	private final String username;
	private final String country;
	private final UserRole role;
}
