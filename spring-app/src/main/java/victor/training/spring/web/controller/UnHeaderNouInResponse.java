package victor.training.spring.web.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnHeaderNouInResponse implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest re = (HttpServletRequest) request;
    String accessToken = re.getHeader("Authorization"); // "Bearer 289356238957389482903158195.23523523852dfgdf234522342342352/.262363423423"

    // PAROLA e rea = iti faliment. nu apare in token,
    // doar username apare
    // daca vrei in app ta sa chemi alt microserviciu, ce pui in Authorization headerul requestului tau catre el?
    //    propagi AT primit de tine catre ALTII pe care-i chemi
    HttpServletResponse resp = (HttpServletResponse) response;
    resp.setHeader("Headeru-Meu", "Ce fain");
    chain.doFilter(request, response);

  }
}
