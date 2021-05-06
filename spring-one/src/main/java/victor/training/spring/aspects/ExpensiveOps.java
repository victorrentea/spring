package victor.training.spring.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
//@Transactional
public class ExpensiveOps {

//	@PreAuthorize()
//	@Async
//	@RolesAllowed(
//
//	)
//	@Retryable

	@Cacheable("primes")
	public Boolean isPrime(int n) {
		new RuntimeException().printStackTrace();
		log.debug("Computing isPrime({})...", n);
		BigDecimal number = new BigDecimal(n);
		final BigDecimal TWO = new BigDecimal("2");
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



	public void anotherMethod() {
		log.debug("Entered another method");
//		cacheManager.getCache("primes").get()
//		ExpensiveOps myselfProxied = (ExpensiveOps) AopContext.currentProxy();
		log.debug("Got: " + isPrime(10000169) + "\n"); // a local method invocation is not proxied !!
	}
//	@Autowired
//	private ExpensiveOps myselfProxied;


	@Autowired
	private UserRepo userRepo;

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public void createUser(User user) {
		userRepo.save(user);
	}
}


@Slf4j
@Service
@RequiredArgsConstructor
class BreakItForTheFramework {
	private final ExpensiveOps ops;
	private final CacheManager cacheManager;

}