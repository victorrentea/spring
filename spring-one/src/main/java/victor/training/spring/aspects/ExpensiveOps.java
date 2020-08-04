package victor.training.spring.aspects;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.criteria.Expression;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExpensiveOps {
	private final static Logger log = LoggerFactory.getLogger(ExpensiveOps.class);
	
	private static final BigDecimal TWO = new BigDecimal("2");


	@Cacheable("primes")
	public Boolean isPrime(int n) {
		new RuntimeException().printStackTrace();
		log.debug("Computing isPrime({})... on {} ", n, this);
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
	@Autowired
	private CacheManager cacheManager;

	@Cacheable("folders")
	public String hashAllFiles(File folder) {
		log.debug("Computing hashAllFiles({})...", folder);

		// ideea 1: muti asta afara din clasa si o chemi printr-o referinta @Autowired de spring
		// ideea 2: autoinject myselfProxied - straniu. da kernel panic la newcomers
		// ideea 3: si mai panic: sa obtii magic acea referinta - nu face
		// ideea 4: renunti la spring si faci treaba programatic: @Cacheable--> CacheManager, *** @Transactional --> TransactionTemplate, @PreAutohorieze --> SecurityContextHolder, @Async->threadPool.submit

		ValueWrapper prime = cacheManager.getCache("primes").get(10000169);
		log.debug("10000169 is prime im va lua din cache sa u imi va calcula din nou??  " + prime.get());

		log.debug("Got: " + myselfProxied.isPrime(10000169) + "\n");


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

	@CacheEvict("folders")
	public void killFolderCache(File folder) {
		// *** Empty. Don not Touch. Let the magic happen. ***
	}
}
