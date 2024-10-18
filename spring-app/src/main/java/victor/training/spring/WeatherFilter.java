package victor.training.spring;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WeatherFilter implements Filter {
  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String username = req.getHeader("x-user");
    System.out.println("Got request from " + username);

    HttpServletResponse resp = (HttpServletResponse) response;
    resp.addHeader("weather", "cloudy");

    chain.doFilter(request, response); // allow other filters to process this incoming request
  }
}
