package victor.training.springdemo.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

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

@Slf4j
@Service
class NumericWorks {
    @Cacheable("prime")
    public boolean isPrime(long n) {
        log.debug("Incep calculul");
        for (Long i = 2L; i <n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Cacheable("foldere")
    @SneakyThrows
    public String hashAllFiles(File folder) {
        log.debug("Computing hashAllFiles({})", folder);
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
