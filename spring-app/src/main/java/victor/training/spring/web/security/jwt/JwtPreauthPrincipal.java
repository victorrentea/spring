package victor.training.spring.web.security.jwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtPreauthPrincipal implements Serializable {
	private final String username;
	private final String country;
	private final String role;
}
