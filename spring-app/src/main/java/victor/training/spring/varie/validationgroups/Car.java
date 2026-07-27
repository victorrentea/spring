package victor.training.spring.varie.validationgroups;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Prima subclasa. Numarul ei de identificare mostenit e, de fapt, un VIN.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {
  private String licensePlate;

  protected Car() { // JPA
  }

  public Car(String vin) {
    super(vin);
  }
}
