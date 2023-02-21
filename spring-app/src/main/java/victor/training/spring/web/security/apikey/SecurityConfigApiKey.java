package victor.training.spring.web.security.apikey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Profile("apikey")
@EnableWebSecurity
public class SecurityConfigApiKey extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // OK since I never take <form> POSTs
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(apiKeyFilter());
    }
    @Bean
    public ApiKeyFilter apiKeyFilter() {
        return new ApiKeyFilter();
    }
}
