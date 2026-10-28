package victor.training.spring.first;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class AmountValidator implements TransactionValidator {
    public List<String> validate(Transaction transaction) {
        if (transaction.amount <= 0) {
            return List.of("Amount must be positive");
        }
        return List.of();
    }
}
