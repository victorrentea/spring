package victor.training.spring.security.config.disabled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;

@Slf4j
@Profile("!form & !jwt & !keycloak & !apikey & !header")
@EnableWebSecurity
public class SecurityConfigDisabled extends WebSecurityConfigurerAdapter {

    @PostConstruct
    public void init() {
        log.warn("Security is DISABLED");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // OK since I never take <form> POSTs
        http.authorizeRequests().anyRequest().permitAll();
    }
}
