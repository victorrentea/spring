package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

@RequiredArgsConstructor
@Service
@Slf4j
public class SecurityService {
  private final UserRepo userRepo;
  private final TrainingRepo trainingRepo;

  public void canEditTraining(Long id) {
    Training training = trainingRepo.findById(id).orElseThrow();
    Long teacherId = training.getTeacher().getId();

    checkManagesTeacher(teacherId);
  }

  public void checkManagesTeacher(Long teacherId) {
    boolean result = managesTeacher(teacherId);
    if (!result) {
      throw new IllegalArgumentException("N-ai voie!");
    }
  }

  public boolean managesTeacher(Long teacherId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepo.findByUsernameForLogin(username).orElseThrow();
    return user.getManagedTeacherIds().contains(teacherId);
  }
}
