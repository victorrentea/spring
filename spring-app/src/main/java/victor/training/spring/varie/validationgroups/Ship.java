package victor.training.spring.varie.validationgroups;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * A doua subclasa. Acelasi camp mostenit, dar aici e un numar IMO, nu un VIN.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("SHIP")
public class Ship extends Vehicle {
  private String flagState;

  protected Ship() { // JPA
  }

  public Ship(String imoNumber) {
    super(imoNumber);
  }
}
