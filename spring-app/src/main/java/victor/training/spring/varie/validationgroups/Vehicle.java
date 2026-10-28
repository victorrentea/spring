package victor.training.spring.varie.validationgroups;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * Superclasa comuna a ierarhiei. Aici traieste campul disputat: {@link #identificationNumber}.
 * <p>
 * PROBLEMA: campul e comun (deci nu-l poti duplica in copii), dar regulile pe el difera
 * radical: pentru {@link Car} e un VIN, pentru {@link Ship} e un numar IMO.
 * <p>
 * PRIMUL PAS: pui pe acelasi camp TOATE constrangerile, dar fiecare marcata cu grupul ei.
 */
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // toata ierarhia intr-un singur tabel
@DiscriminatorColumn(name = "vehicle_type")
public abstract class Vehicle {
  @Id
  @GeneratedValue
  private Long id;

  /**
   * <pre>
   * - {@code @NotBlank} fara groups => cade in grupul implicit 'Default' => se verifica pentru ORICE vehicul
   * - {@code @Pattern}  groups=CarChecks  => se verifica DOAR cand se cere grupul CarChecks
   * - {@code @Pattern}  groups=ShipChecks => se verifica DOAR cand se cere grupul ShipChecks
   * </pre>
   * Cheia: o constrangere care declara groups={X} NU mai face parte din Default.
   * Deci implicit (validator.validate(obj)) nu s-ar verifica nici VIN-ul, nici IMO-ul.
   * <p>
   * Bonus: constrangerile standard sunt @Repeatable, deci poti pune de doua ori @Pattern
   * pe acelasi camp, cu grupuri diferite - fara sa-ti scrii un ConstraintValidator.
   */
  @NotBlank(message = "Numarul de identificare e obligatoriu pentru orice vehicul")
  @Pattern(regexp = "[A-HJ-NPR-Z0-9]{17}", groups = CarChecks.class,
      message = "VIN invalid: 17 caractere majuscule/cifre, fara literele I, O si Q")
  @Pattern(regexp = "IMO\\d{7}", groups = ShipChecks.class,
      message = "Numar IMO invalid: se asteapta 'IMO' urmat de 7 cifre")
  private String identificationNumber;

  protected Vehicle() { // JPA
  }

  protected Vehicle(String identificationNumber) {
    this.identificationNumber = identificationNumber;
  }
}
