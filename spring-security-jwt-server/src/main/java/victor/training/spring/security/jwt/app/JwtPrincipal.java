package victor.training.spring.security.jwt.app;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtPrincipal implements Serializable {
	private final String username;
	private final String country;
}
