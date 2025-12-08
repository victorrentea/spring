package victor.training.spring.first;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChainTest {

  @Autowired
  private Chain chain;

  @Test
  void validate_okTransaction_returnsEmptyList() {
    Transaction t = new Transaction();
    t.amount = 100.0;
    t.to = "GoodGuy";

    List<String> result = chain.validate(t);

    assertNotNull(result);
    assertTrue(result.isEmpty(), "Expected no validation errors");
  }

  @Test
  void validate_negativeAmount_returnsAmountError() {
    Transaction t = new Transaction();
    t.amount = -1.0;
    t.to = "GoodGuy";

    List<String> result = chain.validate(t);

    assertEquals(1, result.size());
    assertTrue(result.contains("Amount must be positive"));
  }

  @Test
  void validate_blacklistedRecipient_returnsToBlacklistedError() {
    Transaction t = new Transaction();
    t.amount = 10.0;
    t.to = "BadGuy";

    List<String> result = chain.validate(t);

    assertEquals(1, result.size());
    assertTrue(result.contains("To is blacklisted"));
  }

  @Test
  void validate_multipleFailures_aggregatesAllErrors() {
    Transaction t = new Transaction();
    t.amount = 0.0; // non-positive
    t.to = "BadGuy";

    List<String> result = chain.validate(t);

    assertEquals(2, result.size());
    assertTrue(result.contains("Amount must be positive"));
    assertTrue(result.contains("To is blacklisted"));
  }
}
