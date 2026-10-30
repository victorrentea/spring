package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component // used from SpEL as @accessController
public class AccessController {
  private final TrainingRepo trainingRepo;
  private final UserRepo userRepo;

  public boolean canDeleteTraining(Long trainingId) {
    Optional<Training> training = trainingRepo.findById(trainingId);
    if (training.isEmpty()) {
      return false;
    }
    return canDeleteTrainingOfTeacher(training.get().teacher().id(), managedTeacherIdsOfCurrentUser());
  }

  public void fillCanDelete(List<TrainingDto> trainings) {
    Set<Long> managedTeacherIds = managedTeacherIdsOfCurrentUser();
    for (TrainingDto training : trainings) {
      training.canDelete = canDeleteTrainingOfTeacher(training.teacherId, managedTeacherIds);
    }
  }

  private boolean canDeleteTrainingOfTeacher(Long teacherId, Set<Long> managedTeacherIds) {
    return hasAuthority("ROLE_TRAINING_DELETE") && managedTeacherIds.contains(teacherId);
  }

  private boolean hasAuthority(String authority) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
      if (grantedAuthority.getAuthority().equals(authority)) {
        return true;
      }
    }
    return false;
  }

  private Set<Long> managedTeacherIdsOfCurrentUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepo.findByUsernameForLogin(username)
            .map(User::getManagedTeacherIds)
            .orElse(Set.of());
  }
}
