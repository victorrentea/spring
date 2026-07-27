package victor.training.spring.varie.validationgroups;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Grupuri de validare intr-o ierarhie de entitati: copiii vor reguli DIFERITE
 * pe acelasi camp mostenit din parinte.
 */
class VehicleValidationGroupsTest {
  /** VIN real de Honda Civic: 17 caractere. Ca numar IMO e o mizerie. */
  private static final String VALID_VIN = "1HGBH41JXMN109186";
  /** Numar IMO real: 'IMO' + 7 cifre. Ca VIN e mult prea scurt. */
  private static final String VALID_IMO = "IMO9074729";

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /** Copil "naiv", fara nimic in plus fata de ce mosteneste. Exista doar ca sa arate capcana. */
  static class Truck extends Vehicle {
    Truck(String identificationNumber) {
      super(identificationNumber);
    }
  }

  @Test
  @DisplayName("0. CAPCANA: regulile puse pe grupuri NU se executa niciodata implicit")
  void groupedRulesAreSilentlySkipped() {
    // o constrangere cu groups=CarChecks a IESIT din grupul Default.
    // Deci un validate() obisnuit nu verifica nici VIN-ul, nici IMO-ul: trece orice.
    assertThat(validator.validate(new Truck("o mizerie oarecare"))).isEmpty();

    // regulile exista, dar trebuie cerute manual - iar apelantul ar trebui sa stie el
    // tipul concret ca sa aleaga grupul potrivit.
    assertThat(validator.validate(new Truck("o mizerie oarecare"), CarChecks.class)).hasSize(1);
    assertThat(validator.validate(new Truck(VALID_VIN), CarChecks.class)).isEmpty();
    assertThat(validator.validate(new Truck(VALID_IMO), ShipChecks.class)).isEmpty();
  }
}
