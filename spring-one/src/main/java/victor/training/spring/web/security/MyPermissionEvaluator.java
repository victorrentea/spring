package victor.training.spring.web.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Course;
import victor.training.spring.web.repo.CourseRepo;

import java.io.Serializable;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class MyPermissionEvaluator implements PermissionEvaluator {
   private final CourseRepo repo;

   @Override
   public boolean hasPermission(
       Authentication auth,
       Object targetDomainObject,
       Object permission) {

      // eg create export
      return true; // TODO 2012-02-3
   }

   @Override
   public boolean hasPermission(
       Authentication auth,
       Serializable targetId,
       String targetType,
       Object permission) {

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

      if ("TRAINING".equals(targetType)) { // NULL-pointer driven dev
         Course course = repo.findById((Long) targetId).get();
         Set<Long> teacherIdsManagedByCurrentUser = securityUser.getManagedTeacherIds();

//         log.info("Is teachedId:{} in {}?", course.getTeacher().getId(), teacherIdsManagedByCurrentUser);
         return teacherIdsManagedByCurrentUser.contains(course.getTeacher().getId());
      }
      return false;
   }
}
