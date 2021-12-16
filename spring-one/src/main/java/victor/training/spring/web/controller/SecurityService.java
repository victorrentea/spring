package victor.training.spring.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;
@Service
public class SecurityService {
   private final TrainingRepo trainingRepo;

   public SecurityService(TrainingRepo trainingRepo) {
      this.trainingRepo = trainingRepo;
   }

   public boolean canDeleteTraining(Long trainingId) {
      Training training = trainingRepo.findById(trainingId).get();

      Long teacherId = training.getTeacher().getId();

      SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return securityUser.getRole().getAuthorities().contains("training.delete")
             && securityUser.getManagedTeacherIds().contains(teacherId);
   }
}