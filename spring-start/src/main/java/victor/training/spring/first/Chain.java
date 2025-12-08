package victor.training.spring.first;

import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Chain {
//  private static final List<TransactionValidator> validators = List.of(
//    new AmountValidator(),
//    new TerroristValidator()
//  );
  private final List<TransactionValidator> validators;// springule, pune-mi toti bean-ii de tip TransactionValidator aici

  public List<String> validate(Transaction transaction) {
    //new AmountValidator(d1,d2);//🤔❌ nu recomand: ca inseamca ca ai un design STATEful
    List<String> list = new ArrayList<>();
    for (TransactionValidator validator : validators) {
      log.info("Aplic validatorul: " + validator);
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

@Service
@Order(20) // sa las loc maine altor validatori intre cei doi existenti
class AmountValidator implements TransactionValidator {
  public List<String> validate(Transaction transaction) {
    if (transaction.amount <= 0) {
      return List.of("Amount must be positive");
    }
    return List.of();
  }
}

//@RequiredArgsConstructor
@Service
@Order(10)
class TerroristValidator implements TransactionValidator {
//  private final TerroristRepo terroristRepo;
  public List<String> validate(Transaction transaction) {
    if (transaction.to.equals("BadGuy")) {
      return List.of("To is blacklisted");
    }
    return List.of();
  }
}
