package victor.training.spring.security.config;

import jakarta.servlet.Filter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.function.Function;

public class AddFilterDSL extends AbstractHttpConfigurer<AddFilterDSL, HttpSecurity> {
    private final Function<AuthenticationManager, Filter> filterFactory;

    AddFilterDSL(Function<AuthenticationManager, Filter> filterFactory) {
      this.filterFactory = filterFactory;
    }

    @Override
    public void configure(HttpSecurity http) {
      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
      var filter = filterFactory.apply(authenticationManager);
      http.addFilter(filter);
    }
    public static AddFilterDSL of(Function<AuthenticationManager, Filter> filterFactory) {
      return new AddFilterDSL(filterFactory);
    }
  }