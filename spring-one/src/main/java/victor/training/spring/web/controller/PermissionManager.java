package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;

@Component
@RequiredArgsConstructor
public class PermissionManager {  // nume default: permissionManager
   private final TrainingRepo trainingRepo;

   public boolean canDeleteTraining(Long id) {
      SecurityUser securityUser = (SecurityUser) SecurityContextHolder
          .getContext().getAuthentication().getPrincipal();

      if (!securityUser.getPermissions().contains("deleteTraining")) {
         return false;
      }

      Training training = trainingRepo.findById(id).get();
//			.orElseThrow(() -> new RuntimeException("N-am id-ul de care zici tu"));
      Long trainingTeacherId = training.getTeacher().getId();

      if (!securityUser.getManagedTeacherIds().contains(trainingTeacherId)) {
        return false;
      }
      return true;
   }
}
