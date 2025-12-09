package victor.training.spring.first;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Chain {
  private static final List<TransactionValidator> validators = List.of(
          new AmountValidator(),
          new TerroristValidator()
  );

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

class AmountValidator implements TransactionValidator {
  public List<String> validate(Transaction transaction) {
    if (transaction.amount <= 0) {
      return List.of("Amount must be positive");
    }
    return List.of();
  }
}

class TerroristValidator implements TransactionValidator {
  public List<String> validate(Transaction transaction) {
    List<String> naughtyList = List.of("BadGuy", "BadGirl");
    if (naughtyList.contains(transaction.to)) {
      return List.of("To is blacklisted");
    }
    return List.of();
  }
}
