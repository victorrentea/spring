package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomHeaderInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("HTTP Interceptor adding custom header to response");
        response.addHeader("Custom-Header", "true");
        return true;
    }
}
