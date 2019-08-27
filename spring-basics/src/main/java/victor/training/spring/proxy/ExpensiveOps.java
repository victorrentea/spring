package victor.training.spring.proxy;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

@Slf4j
@Component
@LoggedClass
public class ExpensiveOps {
	
	private static final BigDecimal TWO = new BigDecimal("2");

	@LoggedMethod
	@Cacheable("primesXX")
	public /*final :p */Boolean isPrime(int n) {
		log.debug("Computing isPrime({})", n);
//		new RuntimeException().printStackTrace(); // uncomment for debugging purposes
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
	private ExpensiveOps myself;

	@Cacheable("folders")
	@SneakyThrows
	public String hashAllFiles(File folder) {
		log.debug("Computing hashAllFiles({})", folder);

//		ExpensiveOps myself= (ExpensiveOps) AopContext.currentProxy();
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + myself.isPrime(10000169) + "\n");

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

	@CacheEvict("folders")
	public void aruncaIntrareaDinCache(File file) {
		// Let the MAGIC happen! Do not touch.
	}

	@CacheEvict(cacheNames = "folders", allEntries = true)
	public void aruncaToateIntrarile() {

	}
}
