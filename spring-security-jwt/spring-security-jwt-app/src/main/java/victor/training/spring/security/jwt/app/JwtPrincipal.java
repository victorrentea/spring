package victor.training.spring.security.jwt.app;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Value
public class JwtPrincipal implements Serializable {
   String username, country;
}
