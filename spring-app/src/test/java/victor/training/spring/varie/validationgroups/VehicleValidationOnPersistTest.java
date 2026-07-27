package victor.training.spring.varie.validationgroups;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Acelasi mecanism, dar vazut din Hibernate: la INSERT/UPDATE, Hibernate ruleaza
 * Bean Validation pe grupul 'Default'. Iar 'Default' e redefinit de @GroupSequence
 * pe fiecare subclasa => baza de date primeste automat regula corecta per tip de vehicul,
 * fara ca serviciul care salveaza sa stie ceva despre grupuri.
 */
@DataJpaTest
class VehicleValidationOnPersistTest {
  private static final String VALID_VIN = "1HGBH41JXMN109186";
  private static final String VALID_IMO = "IMO9074729";

  @Autowired
  VehicleRepo repo;
  @Autowired
  EntityManager entityManager;

  @Test
  @DisplayName("11. Ierarhia se persista in acelasi tabel (SINGLE_TABLE + discriminator)")
  void savesTheWholeHierarchy() {
    repo.saveAll(List.of(new Car(VALID_VIN), new Ship(VALID_IMO)));
    entityManager.flush();
    entityManager.clear();

    List<Vehicle> all = repo.findAll();

    assertThat(all).hasSize(2)
        .extracting(v -> v.getClass().getSimpleName())
        .containsExactlyInAnyOrder("Car", "Ship");
  }

  @Test
  @DisplayName("12. Hibernate refuza INSERT-ul unei masini cu numar de vapor")
  void rejectsCarWithShipIdentifier() {
    repo.save(new Car(VALID_IMO));

    assertThatThrownBy(() -> entityManager.flush())
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("VIN invalid");
  }

  @Test
  @DisplayName("13. ...si simetric, INSERT-ul unui vapor cu VIN")
  void rejectsShipWithCarIdentifier() {
    repo.save(new Ship(VALID_VIN));

    assertThatThrownBy(() -> entityManager.flush())
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("IMO");
  }
}
