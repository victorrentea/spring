package victor.training.spring.transactions;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Profile("!test")
public class SecurityConfigTx extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization on URL-patterns

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
           .csrf().disable()
           .authorizeRequests()
               .mvcMatchers("**").anonymous()
               .anyRequest().authenticated()
           .and()
           .formLogin().permitAll()
            ;
    }

    // ... then, switch to loading user data from DB:
    // *** Also loading data from DB

}
