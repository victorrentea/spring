package victor.training.spring.aspects;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

// TODO interface
@Service
@LoggedClass
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public /*final*/ class ExpensiveOps {
   private final static Logger log = LoggerFactory.getLogger(ExpensiveOps.class);

   private static final BigDecimal TWO = new BigDecimal("2");

//   @Transactional(propagation = Propagation.REQUIRES_NEW)
   @Cacheable("primes")
   public /*final - silently doesnt proxy*/ Boolean isPrime(int n) {
      new RuntimeException().printStackTrace();
      log.debug("Computing isPrime({})...", n);
      BigDecimal number = new BigDecimal(n);
      if (number.compareTo(TWO) <= 0) {
         return true;
      }
      if (number.remainder(TWO).equals(BigDecimal.ZERO)) {
         return false;
      }
      for (BigDecimal divisor = new BigDecimal("3");
           divisor.compareTo(number.divide(TWO)) < 0;
           divisor = divisor.add(TWO)) {
         if (number.remainder(divisor).equals(BigDecimal.ZERO)) {
            return false;
         }
      }
      return true;
   }
   @Autowired
   private ExpensiveOps myselfProxied;

   TransactionTemplate txTemplate;

//   @Component
//   static class Alta {
//      @
//   }
//
//   @Autowired
//   Alta alta;


   @Cacheable("folders")
   public String hashAllFiles(File folder) {
//      txTemplate.sepr
//      txTemplate.execute(status -> {
//         isPrime(10000169);
//         return null;})
//      cm.getCache("folders").get(folder);// cam asta face proxy-ul
      System.out.println("oare ia din cache : " + myselfProxied.isPrime(10_000_169)); // NU IA DIN CACHE PENTRU CA NU MERGE PRIN PROXY LA METODA AIA

      log.debug("Computing hashAllFiles({})...", folder);
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         for (int i = 0; i < 2; i++) { // pretend there is much more work to do here
            Files.walk(folder.toPath())
                .map(Path::toFile)
                .filter(File::isFile)
                .map(Unchecked.function(FileUtils::readFileToString))
                .forEach(s -> md.update(s.getBytes()));
         }
         byte[] digest = md.digest();
         return DatatypeConverter.printHexBinary(digest).toUpperCase();
      } catch (NoSuchAlgorithmException | IOException e) {
         throw new RuntimeException(e);
      }
   }
   @Autowired
   CacheManager cm;

//   @CacheEvict("folders")
   public void curataCachePtFolderul(File file) {
      cm.getCache("folders").evict(file);
      // NOTHING TO DO. EMPTY METHOD. LET THE MAGIC HAPPEN.
   }
}

