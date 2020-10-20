package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;

import java.io.Serializable;
import java.util.Set;

@Component
@Slf4j
public class TrainingAuthorizer  {
   @Autowired
   private TrainingRepo trainingRepo;

   public boolean hasControlOnTraining(long trainingId) {
      SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//      if (securityUser.getPermissions().contains("deleteTraining"))

      Set<Long> teacherIds = securityUser.getManagedTeacherIds();
      log.info("my teacher IDs:" + teacherIds);
      Long teacherId = trainingRepo.findById(trainingId).get().getTeacher().getId();
      log.info("target teacher ID:" + teacherId);
      return teacherIds.contains(teacherId);
   }

}

enum PermissionType {
   READ,
   WRITE
}
