package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.security.SecurityUser;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrainingAuthorizer {
   private final TrainingRepo trainingRepo;

   public boolean authorize(Long trainingId) {
      Training training = trainingRepo.findById(trainingId).orElseThrow(() -> new RuntimeException("Draga hackere, n-ai nimerit id-ul. Mai baga o fisa!?"));

      Long teacherId = training.getTeacher().getId();

      SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      Set<Long> managedTeacherIds = securityUser.getManagedTeacherIds();

      log.info("Is teacher id {} among user teachers: {}", teacherId, managedTeacherIds);
      return managedTeacherIds.contains(teacherId);
   }


}
