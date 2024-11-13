package com.example.demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Filter extends HttpFilter {
  @Override
  protected void doFilter(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    request.getHeaderNames().asIterator().forEachRemaining(name->
        System.out.println(name+":"+request.getHeader(name))
    );

    chain.doFilter(request, response); // lasa request-ul sa continue cu urmatoarele filtre/@RestController

  }
}
