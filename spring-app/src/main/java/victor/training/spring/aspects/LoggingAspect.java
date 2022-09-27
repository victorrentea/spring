package victor.training.spring.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

@Configuration
@ConditionalOnProperty(name = "logging.on", havingValue = "true", matchIfMissing = true)
class LoggingConfig {
    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

}

@Aspect
//@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(victor.training.spring.web.service.Logged) || @within(victor.training.spring.web.service.Logged)") // or
//    @Around("@annotation(victor.training.spring.web.service.Logged)") //method-level annotation
//    @Around("@within(org.springframework.stereotype.Service)") //class annotation
//    @Around("execution(* victor.training.spring.web.service.*.*(..))") // package-level interception
    public Object interceptForLogging(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        log.info("Calling method {} with args {}",methodName, Arrays.toString(args));
        long t0 = currentTimeMillis();
        Object realResult = pjp.proceed(); // actual call happens here
//log?
        long t1 = currentTimeMillis();
        log.info("Took {}", t1-t0);
//        if (realResult instanceof Publisher)
//                return realResult.doOnComplete(() -> {
//                    long t1 = currentTimeMillis();
//                    log.info("Took {}", t1-t0);
//                });
        return realResult;
    }
}
