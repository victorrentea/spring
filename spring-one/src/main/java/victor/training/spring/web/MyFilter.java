package victor.training.spring.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilter implements Filter {

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletResponse re = (HttpServletResponse) response;
      re.addHeader("X","111");
      chain.doFilter(request, response);
   }
}
