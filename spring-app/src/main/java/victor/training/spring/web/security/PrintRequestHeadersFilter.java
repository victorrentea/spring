package victor.training.spring.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
//@Component
@Order(1)
public class PrintRequestHeadersFilter implements Filter {
   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      List<String> headerList = new ArrayList<>();
      for (Enumeration<String> e = httpRequest.getHeaderNames(); e.hasMoreElements(); ) {
         String headerName = e.nextElement();
         headerList.add("\t " + headerName + ": " + httpRequest.getHeader(headerName));
      }
      log.info("Request Headers:\n" + String.join("\n", headerList));
      chain.doFilter(request, response);
   }
}
