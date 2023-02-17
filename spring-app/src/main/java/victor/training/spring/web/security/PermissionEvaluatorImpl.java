package victor.training.spring.web.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class PermissionEvaluatorImpl implements PermissionEvaluator {
   private final TrainingRepo trainingRepo;

   private enum PermissionType {
      WRITE, READ
   }
   @Override
   public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
      if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
         return false;
      }
      String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

      return hasPrivilege(auth, targetType,
          PermissionType.valueOf(permission.toString().toUpperCase()),
          null);
   }

   @Override
   public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
      if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
         return false;
      }
      return hasPrivilege(auth, targetType.toUpperCase(),
          PermissionType.valueOf(permission.toString().toUpperCase()),
          targetId);
   }

   private boolean hasPrivilege(Authentication authentication, String targetType, PermissionType permission, Serializable targetId) {
      switch (targetType) {
         case "TRAINING":
            return hasTrainingPrivilege(authentication, permission, (Long) targetId);
         default:
            throw new IllegalArgumentException("Unknown type: " + targetType);
      }
   }

   private boolean hasTrainingPrivilege(Authentication authentication, PermissionType permission, Long trainingId) {
      if (permission == PermissionType.READ) {
         return true;
      }
      if (authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .noneMatch(t -> t.equals("training.edit"))) {
         log.debug("No authority to edit trainings");
         return false;
      }
      Set<Long> teacherIds = getManagedTeacherIdsFromUsersTable(authentication);
      log.info("Current user manages teacher IDs = {}", teacherIds);
      Long teacherId = trainingRepo.findById(trainingId).get().getTeacher().getId();
      log.info("Training.teacher.id = {}", teacherId);
      return teacherIds.contains(teacherId);
   }

   // keycloak version
   private Set<Long> getManagedTeacherIdsFromAccessToken(Authentication authentication) {
//      KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
//      keycloakPrincipal.getKeycloakSecurityContext().getToken()
//              .getOtherClaims()
      return Collections.emptySet();
   }

   private final UserRepo userRepo;
   private Set<Long> getManagedTeacherIdsFromUsersTable(Authentication authentication) {
      String username = authentication.getName();
      User user = userRepo.findByUsernameForLogin(username)
              .orElseThrow(()->new IllegalArgumentException("User '" + username + "' not found in USERS table."));
      return user.getManagedTeacherIds();
      // When using token-based authorization, the user from DB is typically cached in the user session
   }
}
