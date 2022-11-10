package victor.training.spring.web.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.addHeader("Pot-Din-Filtru", "#sieu");

        response.setTrailerFields(() -> Map.of("Caruta", "Plina"));

        chain.doFilter(req, res); // dau mai departe req catre Spring
    }
}
