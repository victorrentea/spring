package victor.training.micro.jwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtPreauthPrincipal implements Serializable {
	private final String username;
	private final String country;
}
