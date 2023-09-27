package victor.training.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// https://www.baeldung.com/spring-security-run-as-auth
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// !!! REMOVE THIS ANNOTATION FROM THE REST OF THE PROJECT !!! if enable it here
// To use it, place @Secured({"ROLE_USER", "RUN_AS_X"}) to grant a function the "ROLE_RUN_AS_X"
public class RunAsConfig extends GlobalMethodSecurityConfiguration {
  @Override
  protected RunAsManager runAsManager() {
    RunAsManagerImpl runAsManager = new RunAsManagerImpl();
    runAsManager.setKey("MyRunAsKey");
    return runAsManager;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(runAsAuthenticationProvider());
  }

  @Bean
  public AuthenticationProvider runAsAuthenticationProvider() {
    RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();
    authProvider.setKey("MyRunAsKey");
    return authProvider;
  }
}
