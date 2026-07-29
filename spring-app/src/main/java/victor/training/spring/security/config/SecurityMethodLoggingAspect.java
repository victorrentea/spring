package victor.training.spring.security.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityMethodLoggingAspect {

  @Around("@within(org.springframework.security.access.annotation.Secured) || " +
          "@annotation(org.springframework.security.access.annotation.Secured) || " +
          "@within(org.springframework.security.access.prepost.PreAuthorize) || " +
          "@annotation(org.springframework.security.access.prepost.PreAuthorize) || " +
          "@within(org.springframework.security.access.prepost.PostAuthorize) || " +
          "@annotation(org.springframework.security.access.prepost.PostAuthorize)")
  public Object logSecurityDecision(ProceedingJoinPoint pjp) throws Throwable {
    Method method = ((MethodSignature) pjp.getSignature()).getMethod();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String user = Optional.ofNullable(authentication).map(Authentication::getName).orElse("<anonymous>");
    String authorities = Optional.ofNullable(authentication)
            .map(Authentication::getAuthorities)
            .map(values -> values.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(",")))
            .orElse("");
    String methodName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
    String securityAnnotations = securityAnnotations(method);

    log.info("SECURITY METHOD CALL user={} authorities=[{}] method={} annotations={} args={}",
            user, authorities, methodName, securityAnnotations, Arrays.deepToString(pjp.getArgs()));
    try {
      Object result = pjp.proceed();
      log.info("SECURITY METHOD GRANTED user={} method={} result={}", user, methodName, result);
      return result;
    } catch (AccessDeniedException e) {
      log.warn("SECURITY METHOD DENIED user={} authorities=[{}] method={} reason={}",
              user, authorities, methodName, e.getMessage());
      throw e;
    }
  }

  private String securityAnnotations(Method method) {
    return Arrays.stream(new Annotation[]{
                    method.getDeclaringClass().getAnnotation(Secured.class),
                    method.getAnnotation(Secured.class),
                    method.getDeclaringClass().getAnnotation(PreAuthorize.class),
                    method.getAnnotation(PreAuthorize.class),
                    method.getDeclaringClass().getAnnotation(PostAuthorize.class),
                    method.getAnnotation(PostAuthorize.class)
            })
            .filter(annotation -> annotation != null)
            .map(Annotation::toString)
            .collect(Collectors.joining(", "));
  }
}
