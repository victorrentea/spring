package victor.training.spring.security.config.apikey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Profile("apikey")
@EnableWebSecurity
public class SecurityConfigApiKey extends WebSecurityConfigurerAdapter {
    @Value("${api-key:secret}")
    private String apiKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // OK since I never take <form> POSTs
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new ApiKeyFilter(apiKey));
    }

}
