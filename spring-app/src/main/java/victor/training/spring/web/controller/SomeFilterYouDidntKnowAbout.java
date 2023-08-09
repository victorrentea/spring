package victor.training.spring.web.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SomeFilterYouDidntKnowAbout implements Filter {

   private static final Logger log = org.slf4j.LoggerFactory.getLogger(SomeFilterYouDidntKnowAbout.class);

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;

//      ((HttpServletRequest) request).getHeader()
//      ((HttpServletRequest) request).getRequestURI()

      httpResponse.setHeader("X-Custom",  "Value");
      log.error("doFilter stuff");

      // allow the REMAINING FILTERS and eventually @RestController to handle the request
      chain.doFilter(request, response);
   }
}
