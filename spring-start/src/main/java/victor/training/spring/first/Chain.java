package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
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
      if (validator.stopsChain(transaction)) {
        break; // the remaining validators are skipped
      }
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

  default boolean stopsChain(Transaction transaction) {
    return false;
  }
}

@Component
@Order(1) // first in chain: if it ran later, the validators before it could still reject the transaction
class ElonValidator implements TransactionValidator {
  public List<String> validate(Transaction transaction) {
    return List.of();
  }

  @Override
  public boolean stopsChain(Transaction transaction) {
    return "Elon".equals(transaction.to); // we know Elon
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
