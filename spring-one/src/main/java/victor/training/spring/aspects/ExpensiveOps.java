package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ExpensiveOps {

	public Boolean isPrime(int n) {
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

	@Autowired
	private UserRepo userRepo;
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public void createUser(User user) {
		userRepo.save(user);
	}

}
