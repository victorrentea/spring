package victor.training.spring.varie.validationgroups;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.groups.Default;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
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

  @Test
  @DisplayName("6. @GroupSequence e ORDONATA: daca pica Default, grupul urmator nici nu se executa")
  void groupSequenceShortCircuits() {
    // "" pica si pe @NotBlank (Default), si pe @Pattern-ul de IMO (ShipChecks). Dar...
    assertThat(validator.validate(new Ship(""))).hasSize(1); // ...primim O SINGURA eroare, nu doua

    // dovada ca a doua regula chiar ar fi picat: cerem ambele grupuri "pe orizontala",
    // adica fara ordine intre ele => se evalueaza amandoua => 2 erori
    assertThat(validator.validate(new Ship(""), ShipChecks.class, Default.class)).hasSize(2);
  }

  @Test
  @DisplayName("7. A cere explicit Default declanseaza tot secventa redefinita")
  void explicitDefaultGroupTriggersTheSequence() {
    assertThat(validator.validate(new Car(VALID_IMO), Default.class))
        .hasSize(1); // identic cu validate(car) - 'Default' pe Car INSEAMNA secventa
  }

  @Test
  @DisplayName("8. Poti forta orice grup din exterior: @GroupSequence redefineste doar Default-ul")
  void anyGroupCanStillBeRequestedExplicitly() {
    // cerem regulile de VAPOR pe o MASINA (util: acelasi camp verificat 'ca si cum' ar fi altceva)
    Set<ConstraintViolation<Car>> violations = validator.validate(new Car(VALID_VIN), ShipChecks.class);
    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage()).contains("IMO");

    // si ATENTIE la capcana: cerand DOAR ShipChecks, regulile din Default (@NotBlank) NU se mai verifica
    // (null trece de @Pattern - ca orice constrangere bine crescuta, ignora null-ul)
    assertThat(validator.validate(new Car(null), ShipChecks.class)).isEmpty();
  }

  @Test
  @DisplayName("9. Formatul IMO, verificat cu acelasi @Pattern, doar cu alt regex si alt grup")
  void imoFormat() {
    assertThat(validator.validate(new Ship("IMO9074729"))).isEmpty();  // corect
    assertThat(validator.validate(new Ship("9074729"))).hasSize(1);    // fara prefixul IMO
    assertThat(validator.validate(new Ship("IMO907472"))).hasSize(1);  // doar 6 cifre
    assertThat(validator.validate(new Ship("IMO90747299"))).hasSize(1);// 8 cifre
    // (cifra de control reala din IMO n-o poate face un regex - acolo ai avea nevoie
    //  de un ConstraintValidator custom, care oricum ar declara acelasi groups=ShipChecks)
  }

  @Test
  @DisplayName("10. Cascadare @Valid peste o colectie polimorfica: fiecare element cu grupul lui")
  void cascadingOverAPolymorphicCollection() {
    Fleet fleet = new Fleet(List.of(
        new Car(VALID_VIN),  // ok
        new Ship(VALID_IMO), // ok
        new Ship(VALID_VIN)  // vapor cu VIN => pica
    ));

    Set<ConstraintViolation<Fleet>> violations = validator.validate(fleet);

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getPropertyPath())
        .hasToString("vehicles[2].identificationNumber");
  }
}
