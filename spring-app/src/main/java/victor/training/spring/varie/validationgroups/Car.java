package victor.training.spring.varie.validationgroups;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.GroupSequence;
import lombok.Getter;
import lombok.Setter;

/**
 * TRUCUL CENTRAL: <b>@GroupSequence redefineste ce inseamna "Default" pentru ACEASTA clasa</b>.
 * <p>
 * {@code @GroupSequence({Car.class, CarChecks.class})} spune:
 * "cand cineva valideaza o Masina pe grupul Default, verifica intai constrangerile Default
 * (Car.class = grupul Default al clasei, inclusiv cele mostenite din Vehicle),
 * apoi, DOAR daca acelea au trecut, si constrangerile din CarChecks".
 * <p>
 * Consecinte:
 * <ul>
 *   <li>apelantul nu mai trebuie sa stie de grupuri: {@code validator.validate(car)} e suficient;</li>
 *   <li>selectia grupului se face dupa tipul REAL al obiectului (polimorfism), nu dupa cel declarat;</li>
 *   <li>secventa e ordonata => scurt-circuiteaza: daca pica Default, CarChecks nici nu se mai executa.</li>
 * </ul>
 * Regula sintactica: secventa TREBUIE sa contina clasa pe care e pusa adnotarea (Car.class aici),
 * si NU are voie sa contina Default.class (ar fi circular => GroupDefinitionException).
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("CAR")
@GroupSequence({Car.class, CarChecks.class})
public class Car extends Vehicle {
  private String licensePlate;

  protected Car() { // JPA
  }

  public Car(String vin) {
    super(vin);
  }
}
