package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;

import java.io.Serializable;
import java.util.Set;

enum PermissionType {
   READ, UPDATE, DELETE
}
@Slf4j
@Component
public class MyPermissionEvaluator implements PermissionEvaluator {
   @Autowired
   private TrainingRepo trainingRepo;

   @Override
   public boolean hasPermission(
       Authentication auth, Object targetDomainObject, Object permission) {
      if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
         return false;
      }
      String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

      return hasPrivilege((SecurityUser) auth.getPrincipal(), targetType,
          PermissionType.valueOf(permission.toString().toUpperCase()),
          null);
   }

   @Override
   public boolean hasPermission(
       Authentication auth, Serializable targetId, String targetType, Object permission) {
      if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
         return false;
      }
      return hasPrivilege((SecurityUser) auth.getPrincipal(), targetType.toUpperCase(),
          PermissionType.valueOf(permission.toString().toUpperCase()),
          targetId);
   }

   private boolean hasPrivilege(SecurityUser securityUser, String targetType, PermissionType permission, Serializable targetId) {
      switch (targetType) {
         case "TRAINING":
            return hasTrainingPrivilege(securityUser, permission, (Long) targetId);
         default:
            throw new IllegalArgumentException("Unknown type: " + targetType);
      }
   }

   private boolean hasTrainingPrivilege(SecurityUser securityUser, PermissionType permission, Long trainingId) {
      if (permission == PermissionType.READ) {
         return true;
      }
      if (!securityUser.getPermissions().contains("deleteTraining")) {
         return false;
      }
      Set<Long> teacherIds = securityUser.getManagedTeacherIds();
      log.info("my teacher IDs:" + teacherIds);
      Long teacherId = trainingRepo.findById(trainingId).get().getTeacher().getId();
      log.info("target teacher ID:" + teacherId);
      return teacherIds.contains(teacherId);
   }
}
