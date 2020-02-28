package victor.training.spring.security.jwt.app;

import java.io.Serializable;


public class UsernameContextPrincipal implements Serializable {
	public enum AuthnContext {
		LOW, MEDIUM, HIGH
	}

	private final String username;
	private final AuthnContext context;

	public UsernameContextPrincipal(String username, AuthnContext context) {
		this.username = username;
		this.context = context;
	}

	public String getUsername() {
		return username;
	}

	public AuthnContext getContext() {
		return context;
	}
	
	
	
}
