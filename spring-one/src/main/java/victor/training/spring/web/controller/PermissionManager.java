package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Course;
import victor.training.spring.web.repo.CourseRepo;
import victor.training.spring.web.security.SecurityUser;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class PermissionManager {

   private final CourseRepo repo;

   public boolean hasPermission(long courseId) {
      Course course = repo.findById(courseId).get();

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

      Set<Long> teacherIdsManagedByCurrentUser = securityUser.getManagedTeacherIds();
      log.info("Is teachedId:{} in {}?", course.getTeacher().getId(), teacherIdsManagedByCurrentUser);
      return teacherIdsManagedByCurrentUser.contains(course.getTeacher().getId());
   }
}
