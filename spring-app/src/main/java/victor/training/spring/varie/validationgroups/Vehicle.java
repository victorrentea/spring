package victor.training.spring.varie.validationgroups;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Superclasa comuna a ierarhiei. Aici traieste campul disputat: {@link #identificationNumber}.
 * <p>
 * PROBLEMA pe care o vom rezolva pas cu pas: campul e comun (deci nu-l poti duplica in copii),
 * dar regulile pe el difera radical intre subclase: pentru {@link Car} numarul de identificare
 * e un VIN (17 caractere), iar pentru {@link Ship} e un numar IMO ("IMO" + 7 cifre).
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

  /** Deocamdata o singura regula, cea comuna: orice vehicul are un numar de identificare. */
  @NotBlank(message = "Numarul de identificare e obligatoriu pentru orice vehicul")
  private String identificationNumber;

  protected Vehicle() { // JPA
  }

  protected Vehicle(String identificationNumber) {
    this.identificationNumber = identificationNumber;
  }
}
