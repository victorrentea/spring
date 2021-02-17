package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;

@Component
public class PermissionChecker {
   @Autowired
   private TrainingRepo repo;

   public boolean checkPermission(Long id) {
      Training training = repo.findById(id).get();// imi bag picioru'! De unde mama lui are asta ID-ul? nu-i intorc 404 ci ce-mi vine mai la indemana

      Long teacherId = training.getTeacher().getId();

      SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return securityUser.getManagedTeacherIds().contains(teacherId);
   }
}
