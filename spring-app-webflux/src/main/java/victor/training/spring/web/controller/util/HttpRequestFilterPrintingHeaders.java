package victor.training.spring.web.controller.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Component
public class HttpRequestFilterPrintingHeaders implements WebFilter {
//   @Override
//   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//      HttpServletRequest httpRequest = (HttpServletRequest) request;
//      List<String> headerList = new ArrayList<>();
//      for (Enumeration<String> e = httpRequest.getHeaderNames(); e.hasMoreElements(); ) {
//         String headerName = e.nextElement();
//         headerList.add("\t " + headerName + ": " + httpRequest.getHeader(headerName));
//      }
//      log.debug("Request Headers:\n" + String.join("\n", headerList));
//      chain.doFilter(request, response);
//   }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String s = headers.entrySet().stream().map(e -> "\t " + e.getKey() + ": " + e.getValue()).collect(Collectors.joining("\n"));
        System.out.println("Request headers : " + s);

        exchange.getResponse().getHeaders().add("X-Resp","value");
        return chain.filter(exchange);
    }
}
