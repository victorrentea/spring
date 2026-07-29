package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import victor.training.spring.web.config.JpaAuditingConfig;
import victor.training.spring.web.entity.Training;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@DataJpaTest
@ContextConfiguration(classes = TrainingAuditingTest.JpaConfig.class)
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
class TrainingAuditingTest {

  private final TrainingRepo trainingRepo;

  @Test
  @WithMockUser(username = "alice")
  void storesCurrentUserAsCreatorAndModifier() {
    Training savedTraining = trainingRepo.saveAndFlush(new Training("Spring", LocalDate.of(2026, 1, 20)));

    assertThat(savedTraining.getCreatedBy()).isEqualTo("alice");
    assertThat(savedTraining.getModifiedBy()).isEqualTo("alice");
  }

  @Configuration
  @Import(JpaAuditingConfig.class)
  @EnableJpaRepositories(basePackageClasses = TrainingRepo.class)
  @EntityScan(basePackageClasses = Training.class)
  static class JpaConfig {
  }
}
