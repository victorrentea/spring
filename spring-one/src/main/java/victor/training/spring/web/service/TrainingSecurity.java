package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class TrainingSecurity {
   private final TrainingRepo trainingRepo;


   public void checkCanUpdateTraining(Long id) {
      SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Set<Long> allowedTeacherIds = securityUser.getManagedTeacherIds();

      Training training = trainingRepo.findById(id).get();
      Long teacherId = training.getTeacher().getId();

      if (!allowedTeacherIds.contains(teacherId)) {
         throw new RuntimeException("Not allowed");
      }
   }
}
