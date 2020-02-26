package victor.training.springdemo.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;

@Slf4j
@EnableCaching
@SpringBootApplication
public class AopApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AopApp.class, args);
    }
    @Autowired
    private NumericWorks works;
    @Override
    public void run(String... args) throws Exception {
        log.debug("Start");
        log.debug("Mi-a dat Spring un : " + works.getClass());
        log.debug("O fi prim? " + works.isPrime(10_000_169));
        log.debug("O fi prim? " + works.isPrime(10_000_169));

        log.debug(works.hashAllFiles(new File("..")));
        Thread.sleep(2000);
        // Magic, te prinzi ca s-a schimbat un fisier: NIO

        works.clearFolderCache(new File(".."));

        log.debug(works.hashAllFiles(new File("..")));
    }
}
@Retention(RetentionPolicy.RUNTIME)
@interface LoggedClass {}
@Component
@Aspect
@Slf4j
//@ORder se poate dar e rau
class LoggingAspect {

//    @Around("execution(* victor.training.springdemo.aop..*.*(..))") // package-name filtering
    @Around("execution(* *(..)) && @within(victor.training.springdemo.aop.LoggedClass)") // package-name filtering
    public Object intercept(ProceedingJoinPoint point) throws Throwable {
        log.debug("Calling {} cu param {}", point.getSignature().getName(),
                Arrays.toString(point.getArgs()));
        return point.proceed();
    }
}

@Slf4j
@Service
@LoggedClass
class NumericWorks {
    @Cacheable("prime")
//    @Transactional
    public boolean isPrime(long n) {
//        new RuntimeException().printStackTrace();
        log.debug("Incep calculul");
        for (Long i = 2L; i <n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Autowired
    private NumericWorks totEuDarProxiat;

    @Cacheable("foldere")
    @SneakyThrows
    public String hashAllFiles(File folder) {
        log.debug("Computing hashAllFiles({})", folder);
        System.out.println(totEuDarProxiat.isPrime(10_000_169));
        MessageDigest md = MessageDigest.getInstance("MD5");
        for (int i = 0; i < 3; i++) { // pretend there is much more work to do here
            Files.walk(folder.toPath())
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .map(Unchecked.function(FileUtils::readFileToString))
                    .forEach(s -> md.update(s.getBytes()));
        }
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    @CacheEvict("foldere")
    public void clearFolderCache(File folder) {
        // NIMIC! Empty Method. Do NOT Touch. Let the magic happen.
        // chem functia asta doar ca sa se prinda proxu-ul ca tre sa arunce la gunoi cacheul
    }
}
