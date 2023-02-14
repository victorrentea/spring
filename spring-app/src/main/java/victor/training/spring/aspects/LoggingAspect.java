package victor.training.spring.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.joining;

@Slf4j
@Aspect
@Component
@Order(5)
// TODO#1 : enable this aspect only if there is no property logging.aspect=false
//@ConditionalOnProperty(value = "logging.aspect",havingValue = "true", matchIfMissing = true)
// TODO#2 : enable this aspect only if there is no sleuth in the classpath
//@ConditionalOnMissingClass("org.springframework.cloud.sleuth.brave.bridge.BraveHttpClientHandler")

//@ConditionalOnMissingBean // eg: if the dev did not define a bean of a certain type, define one automatically
// using this the sql.DataSource is defined by spring

@ConditionalOnExpression("'${logging.aspect:true}' == 'true'")

public class LoggingAspect {

    @Around("execution(* *.sum(..))")
    public Object sum(ProceedingJoinPoint pjp) throws Throwable {
        Object r = pjp.proceed();
        log.info("🎉🎉🎉🎉🎉calling {} with {} returned {}",
                pjp.getSignature().getName(), List.of(pjp.getArgs()), r);
        return r;
    }




    @Around("@within(victor.training.spring.aspects.Facade))") // method of @Facade classes
//        @Around("@annotation(victor.training.spring.aspects.LoggedMethod))") // @LoggedMethod method
    //    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))") // all subtypes of JpaRepository

    // -- DANGER ZONE --
    //    @Around("execution(* victor.training.spring.web..*.get*(..))") // any method of any class in a sub-package of 'web'
    //    @Around("execution(* *.get*(..))") // all methods starting with "get" everywhere!! = naming convention = dangerous😱
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        if (log.isDebugEnabled()) {
            String methodName = joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName();
            String currentUsername = "TODO";//SecurityContextHolder.getContext().getName()";
            String argListConcat = Stream.of(joinPoint.getArgs()).map(this::jsonify).collect(joining(","));
            log.debug("Invoking {}(..) (user:{}): {}", methodName, currentUsername, argListConcat);
        }

        try {
            Object returnedObject = joinPoint.proceed(); // allow the call to propagate to original (intercepted) method

            if (log.isDebugEnabled()) {
                log.debug("Returned value: {}", jsonify(returnedObject));
            }
            return returnedObject;
        } catch (Exception e) {
            log.error("Threw exception {}: {}", e.getClass(), e.getMessage());
            throw e;
        }
    }

    private final ObjectMapper jackson = new ObjectMapper();
    @PostConstruct
    public void configureMapper() {
        if (log.isTraceEnabled()) {
            log.trace("JSON serialization will be indented");
            jackson.enable(SerializationFeature.INDENT_OUTPUT);
        }
    }
    private String jsonify(Object object) {
        if (object == null) {
            return "<null>";
        } else if (object instanceof OutputStream) {
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

}

