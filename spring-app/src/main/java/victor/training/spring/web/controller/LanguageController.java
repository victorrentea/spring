package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final ProgrammingLanguageRepo repo;
    private final LanguageService languageService;

    @GetMapping
    // TODO cache
    // TODO evict via dedicated endpoint (called from script)
    public List<LanguageDto> getAll() {
        log.debug("Chem fct pe " + languageService.getClass());
        return languageService.getLanguages();
    }

    @Autowired
    private LanguageController selfProxied; // solutie carpit

    @Cacheable("countries")
    public List<LanguageDto> getLanguages() {
        log.debug("in fct");
        new RuntimeException().printStackTrace();
        return repo.findAll().stream().map(LanguageDto::new).collect(Collectors.toList());
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Auditata {
    String value();
}

@Slf4j
@Aspect
@Component
@Order(10)
class AspectuMeu {

//    @Around("execution(* getLanguages(..))") // niciodata
    @Around("@annotation(victor.training.spring.web.controller.Auditata)")
    public Object method(ProceedingJoinPoint pjp) throws Throwable {
        long t0 = currentTimeMillis();
//        Method declaredMethod = pjp.getSignature().getDeclaringType().getDeclaredMethod(pjp.getSignature().getName());
//        Auditata a = declaredMethod.getAnnotation(Auditata.class);
//        a.value()
        Object result = pjp.proceed(); // aici chemi metoeda reala.
        long t1 = currentTimeMillis();
        log.debug("Metoda {} a durat {}  ms", pjp.getSignature().getName() , t1 - t0);
        return result;
    }
}


//class Tzeapa extends LanguageService { // generata la runtime in JVM nu de catre javac
//    @Override
//    public List<LanguageDto> getLanguages() {
//daca am in cache da din cacjhe altfel cheam-o si apoi pune0n cache
//        return super.getLanguages();
//    }
//}