package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

@Component
public class PermissionManager {
    public boolean canDeleteTraining(Long id) {
        Training training = trainingRepo.findById(id).orElseThrow();
        User currentUser = userRepo.getForLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        Long teacherId = training.getTeacher().getId();
        return currentUser.getManagedTeacherIds().contains(teacherId);
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainingRepo trainingRepo;
}
