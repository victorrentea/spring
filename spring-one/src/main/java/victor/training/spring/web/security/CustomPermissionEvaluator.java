package victor.training.spring.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;

import java.io.Serializable;

@Slf4j
//@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

   @Override
   public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
      throw new NotImplementedException();
   }

   @Override
   public boolean hasPermission(
       Authentication auth, Serializable targetId, String targetType, Object permission) {
      if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
         return false;
      }


      SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
      if ("TRAINING".equals(targetType) && "delete".equals(permission)) {
         log.info("Trying to delete a training");
         SimpleGrantedAuthority deleteAuthority = new SimpleGrantedAuthority("deleteTraining");
         if (!securityUser.getAuthorities().contains(deleteAuthority)) {
            return false;
         }

         log.info("User teacher Ids : " + securityUser.getManagedTeacherIds() + " contains? " + targetId);
         Training training = trainingRepo.findById((Long) targetId).get();
         return securityUser.getManagedTeacherIds().contains(training.getTeacher().getId());
      }

      return false;
   }

   @Autowired
   private TrainingRepo trainingRepo;


}