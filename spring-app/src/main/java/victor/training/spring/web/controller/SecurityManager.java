package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

@RequiredArgsConstructor
@Component
public class SecurityManager {
  private final TrainingRepo trainingRepo;
  private final UserRepo userRepo;

  public boolean canDeleteTraining(Long trainingId) {
    Training training = trainingRepo.findById(trainingId).orElseThrow();
    ProgrammingLanguage language = training.getProgrammingLanguage();

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepo.findByUsernameForLogin(username).orElseThrow();
    ProgrammingLanguage userLanguage = user.getAdminForLanguage();

    return userLanguage == language;
  }
}
