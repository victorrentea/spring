package victor.training.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

import static java.lang.String.join;
import static java.util.Collections.list;
import static java.util.stream.Collectors.joining;

@Slf4j
@Component
@Order(SecurityProperties.DEFAULT_FILTER_ORDER - 1000) // run in before Spring's Security Filter Chain
public class HeaderPrinterFilter extends HttpFilter {
   // DO NOT DO THIS IN PRODUCTION: security breach (print the Bearer token can get you fired)
   @Override
   protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      log.info("\nRequest Headers for "  + request.getRequestURI()+"\n"
               + getHeadersAsMap(list(request.getHeaderNames()), name -> list(request.getHeaders(name))));
      chain.doFilter(request, response);
      log.info("\nResponse Headers for "  + request.getRequestURI()+"\n"
               + getHeadersAsMap(response.getHeaderNames(), response::getHeaders));
   }

   private static String getHeadersAsMap(Collection<String> names, Function<String, Collection<String>> valueByName) {
      return names.stream()
              .sorted()
              .map(name -> "\t" + name + ": " + join("; ", valueByName.apply(name)))
              .collect(joining("\n"));
   }
}
