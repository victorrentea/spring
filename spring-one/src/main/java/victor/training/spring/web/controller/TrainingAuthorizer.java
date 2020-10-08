package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

@Component
@RequiredArgsConstructor
public class TrainingAuthorizer {
   private final TrainingRepo trainingRepo;
   private final UserRepo userRepo;

   public void authorize(Long trainingId) {
      Training training = trainingRepo.findById(trainingId).orElseThrow(() -> new RuntimeException("Draga hackere, n-ai nimerit id-ul. Mai baga o fisa!?"));

      Long teacherId = training.getTeacher().getId();
      String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

      User user = userRepo.findByUsername(currentUsername);

      if (!user.getManagedTeacherIds().contains(teacherId)) {
         throw new IllegalArgumentException("N-ai voie!");
      }
   }


}
