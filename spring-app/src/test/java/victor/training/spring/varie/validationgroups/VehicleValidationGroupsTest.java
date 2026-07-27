package victor.training.spring.varie.validationgroups;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

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

  /** Copil "naiv", fara @GroupSequence. Exista doar ca sa arate capcana din testul 0. */
  static class Truck extends Vehicle {
    Truck(String identificationNumber) {
      super(identificationNumber);
    }
  }

  @Test
  @DisplayName("0. CAPCANA: fara @GroupSequence, regulile pe grupuri NU se executa niciodata implicit")
  void withoutGroupSequence_theGroupedRulesAreSilentlySkipped() {
    // o constrangere cu groups=CarChecks a IESIT din grupul Default.
    // Deci un validate() obisnuit pe Truck nu verifica nici VIN-ul, nici IMO-ul: trece orice.
    assertThat(validator.validate(new Truck("o mizerie oarecare"))).isEmpty();

    // regula exista, dar trebuie ceruta manual - iar apelantul ar trebui sa stie el
    // tipul concret ca sa aleaga grupul potrivit. Exact munca pe care o preia @GroupSequence.
    assertThat(validator.validate(new Truck("o mizerie oarecare"), CarChecks.class)).hasSize(1);
  }

  @Test
  @DisplayName("1. Fericit: fiecare copil trece cu numarul lui de identificare")
  void happy() {
    assertThat(validator.validate(new Car(VALID_VIN))).isEmpty();
    assertThat(validator.validate(new Ship(VALID_IMO))).isEmpty();
  }

  @Test
  @DisplayName("2. ESENTA: acelasi string e VALID pentru Car si INVALID pentru Ship")
  void sameValue_validAsCar_invalidAsShip() {
    assertThat(validator.validate(new Car(VALID_VIN))).isEmpty();

    Set<ConstraintViolation<Ship>> violations = validator.validate(new Ship(VALID_VIN));

    assertThat(violations).hasSize(1);
    ConstraintViolation<Ship> violation = violations.iterator().next();
    assertThat(violation.getPropertyPath()).hasToString("identificationNumber");
    assertThat(violation.getMessage()).contains("IMO");
  }

  @Test
  @DisplayName("3. ...si invers: numarul IMO e valid pe vapor, dar respins pe masina")
  void sameValue_validAsShip_invalidAsCar() {
    assertThat(validator.validate(new Ship(VALID_IMO))).isEmpty();

    Set<ConstraintViolation<Car>> violations = validator.validate(new Car(VALID_IMO));

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage()).contains("VIN invalid");
  }

  @Test
  @DisplayName("4. Selectia grupului se face dupa tipul REAL, nu dupa cel declarat (polimorfism)")
  void groupIsChosenByRuntimeType() {
    Vehicle declaredAsVehicle = new Car(VALID_IMO); // static: Vehicle, la runtime: Car

    Set<ConstraintViolation<Vehicle>> violations = validator.validate(declaredAsVehicle);

    // desi apelantul "vede" doar un Vehicle, Hibernate Validator citeste obj.getClass()
    // => aplica @GroupSequence de pe Car => regula de VIN
    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage()).contains("VIN invalid");
  }

  @Test
  @DisplayName("5. Regula comuna (Default) ramane comuna: e ceruta pentru ambii copii")
  void commonRuleStillAppliesToBoth() {
    assertThat(validator.validate(new Car("")))
        .extracting(v -> v.getMessage())
        .containsExactly("Numarul de identificare e obligatoriu pentru orice vehicul");
    assertThat(validator.validate(new Ship(null)))
        .extracting(v -> v.getMessage())
        .containsExactly("Numarul de identificare e obligatoriu pentru orice vehicul");
  }
}
