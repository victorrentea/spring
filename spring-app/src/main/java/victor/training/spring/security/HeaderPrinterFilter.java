package victor.training.spring.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

import static java.lang.String.join;
import static java.util.Collections.list;
import static java.util.stream.Collectors.joining;

@Slf4j
@Component
// WARNING: do not enable in prod - print sensitive headers (like Authorization: )
@ConditionalOnProperty(name = "print.request.headers", havingValue = "true")
@Order(SecurityProperties.DEFAULT_FILTER_ORDER - 1000) // run before Spring's Security Filter Chain
public class HeaderPrinterFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("\nRequest Headers for " + request.getRequestURI() + "\n" +
                getHeadersAsMap(list(request.getHeaderNames()), name -> list(request.getHeaders(name))));
        chain.doFilter(request, response);
        //log.info("\nResponse Headers for "  + request.getRequestURI()+"\n" + getHeadersAsMap(response.getHeaderNames(), response::getHeaders));
    }

    private static String getHeadersAsMap(Collection<String> names, Function<String, Collection<String>> valueByName) {
        return names.stream()
                .sorted()
                .map(name -> "\t" + name + ": " + join("; ", valueByName.apply(name)))
                .collect(joining("\n"));
    }
}
