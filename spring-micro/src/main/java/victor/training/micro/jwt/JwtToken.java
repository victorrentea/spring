package victor.training.micro.jwt;

import lombok.Data;

import java.io.Serializable;

// extracted from received JWT token
@Data
public class JwtToken implements Serializable {
	private final String username;
	private final String country;
	private final String role;
}
