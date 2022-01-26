package victor.training.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

@Configuration
//@ConditionalOnClass(SecurityException.class)
public class ConditionalSample {
   @Bean
   public Filter method() {
      return new Filter() {
         @Override
         public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("MEMEME");
            chain.doFilter(request, response);
         }
      };
   }
}
