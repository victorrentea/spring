package victor.training.spring.aspects;

import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service // singleton
@Logged
public/* final ERROR*/ class ExpensiveOps {
	private final static Logger log = LoggerFactory.getLogger(ExpensiveOps.class);

	private static final BigDecimal TWO = new BigDecimal("2");

	@Autowired
	private CacheManager cacheManager;

	@Cacheable("primes")
	public /*final silently skipped*/ Boolean isPrime(int n) {
//		Boolean cached = cacheManager.getCache("primes").get(n, Boolean.class);
//		if (cached != null) {
//			return cached;
//		}

		log.debug("Computing isPrime({})...", n);
		new RuntimeException("Intentionat, sa apara stack trace").printStackTrace();
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
//		cacheManager.getCache("primes").put(n, true);
		return true;
	}

	public void oareMergeCacheableDacaInvocareaELocala_NU() {
		// atunci cand invoci o metoda adnotata (eg @Transactional) din aceeasi clasa,
		// adnotarea (Proxy-ul) nu functioneaza.

		// Proxy-urile spring by default merg doar cand invoci o metoda
		// pe o referinta luata de la spring
		log.debug("10000169 is prime ? ");
		log.debug("Got3: " + self.isPrime(10000169) + "\n");
	}

	@Autowired
	private ExpensiveOps self;


	public String hashAllFiles(File folder) {
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
}
