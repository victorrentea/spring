package victor.training.spring.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.stream.Collectors.joining;

@Slf4j
@Aspect
@Component
public class FacadeLoggingInterceptor {
   private final ObjectMapper jackson = new ObjectMapper();

   @PostConstruct
   public void configureMapper() {
      if (log.isTraceEnabled()) {
         jackson.enable(SerializationFeature.INDENT_OUTPUT);
      }
   }

   @Around("@within(victor.training.spring.aspects.Facade))")
   public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
      logBefore(joinPoint);
      long t0 = currentTimeMillis();
		try {
			Object returnedObject = joinPoint.proceed();
			logAfter(joinPoint, returnedObject, t0);
         return returnedObject;
      } catch (Exception e) {
         long deltaMillis = currentTimeMillis() - t0;
         log.error("Exception ({} ms): {}: {}",
             deltaMillis, e.getClass(), e.getMessage());
         throw e;
      }
   }

   private void logBefore(ProceedingJoinPoint joinPoint) {
      if (!log.isDebugEnabled()) {
         return;
      }
      String argListConcat = getArgumentsString(joinPoint);

      String currentUsername = "SecurityContextHolder.getContext().getName()"; // TODO

      log.debug("Invoking {}.{}(..) (user:{}): {}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(),
			currentUsername, argListConcat);
   }

   @NotNull
   private String getArgumentsString(ProceedingJoinPoint joinPoint) {
         return Stream.of(joinPoint.getArgs())
             .map(this::objectToJson)
             .map(Object::toString)
             .collect(joining(","));
   }

   private String objectToJson(Object object) {
      if (object instanceof OutputStream) {
         return "<OutputStream>";
      } else if (object instanceof InputStream) {
         return "<InputStream>";
      } else {
         try {
            return jackson.writeValueAsString(object);
         } catch (JsonProcessingException e) {
            log.warn("Could not serialize as JSON (stacktrace on TRACE): " + e);
            log.trace("Cannot serialize value: " + e, e);
            return "<JSONERROR>";
         }
      }
   }

   private void logAfter(ProceedingJoinPoint joinPoint, Object returnedObject, long t0) {
		if (!log.isDebugEnabled()) {
			return;
		}
		long deltaMillis = currentTimeMillis() - t0;
		String returnString = objectToJson(returnedObject);
		log.debug("Return from {}.{} ({} ms): {}",
			 joinPoint.getTarget().getClass().getSimpleName(),
			 joinPoint.getSignature().getName(),
			 deltaMillis,
			 returnString);
	}

}

@Retention(RUNTIME)
@interface Facade {
}