package victor.training.spring.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import victor.training.spring.security.config.SecurityMethodLoggingAspect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(SecurityMethodLoggingAspectTest.Config.class)
@ExtendWith(OutputCaptureExtension.class)
class SecurityMethodLoggingAspectTest {

  @Autowired
  private SecuredService securedService;

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  void logsGrantedAccessForSecuredMethod(CapturedOutput output) {
    securedService.adminOnly();

    assertThat(output).contains(
            "SECURITY METHOD CALL user=admin",
            "SecuredService.adminOnly",
            "SECURITY METHOD GRANTED user=admin");
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void logsDeniedAccessForSecuredMethod(CapturedOutput output) {
    assertThatThrownBy(() -> securedService.adminOnly())
            .isInstanceOf(AccessDeniedException.class);

    assertThat(output).contains(
            "SECURITY METHOD CALL user=user",
            "SecuredService.adminOnly",
            "SECURITY METHOD DENIED user=user");
  }

  @Configuration
  @EnableMethodSecurity(securedEnabled = true)
  @EnableAspectJAutoProxy(proxyTargetClass = true)
  static class Config {
    @Bean
    SecurityMethodLoggingAspect securityMethodLoggingAspect() {
      return new SecurityMethodLoggingAspect();
    }

    @Bean
    SecuredService securedService() {
      return new SecuredService();
    }
  }

  static class SecuredService {
    @Secured("ROLE_ADMIN")
    void adminOnly() {
    }
  }
}
