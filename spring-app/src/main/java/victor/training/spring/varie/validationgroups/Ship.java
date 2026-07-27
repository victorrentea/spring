package victor.training.spring.varie.validationgroups;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.GroupSequence;
import lombok.Getter;
import lombok.Setter;

/**
 * Simetric fata de {@link Car}: acelasi camp mostenit, alt grup activat.
 * Aceeasi valoare a lui identificationNumber va fi acceptata aici si respinsa dincolo.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("SHIP")
@GroupSequence({Ship.class, ShipChecks.class})
public class Ship extends Vehicle {
  private String flagState;

  protected Ship() { // JPA
  }

  public Ship(String imoNumber) {
    super(imoNumber);
  }
}
