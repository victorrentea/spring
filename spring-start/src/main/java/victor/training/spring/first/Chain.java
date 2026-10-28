package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Chain {
  private final List<TransactionValidator> validators; // Spring injects all beans implementing the interface

  public List<String> validate(Transaction transaction) {
    List<String> list = new ArrayList<>();
    for (TransactionValidator validator : validators) {
      list.addAll(validator.validate(transaction));
    }
    return list;
  }
}

class Transaction {
  Double amount;
  String currency;
  String from;
  String to;
  Double comision;
}

interface TransactionValidator {
  List<String> validate(Transaction transaction);
}

@Component
class AmountValidator implements TransactionValidator {
  public List<String> validate(Transaction transaction) {
    if (transaction.amount <= 0) {
      return List.of("Amount must be positive");
    }
    return List.of();
  }
}

@Component
class TerroristValidator implements TransactionValidator {
  public List<String> validate(Transaction transaction) {
    List<String> naughtyList = List.of("BadGuy", "BadGirl");
    if (naughtyList.contains(transaction.to)) {
      return List.of("To is blacklisted");
    }
    return List.of();
  }
}
