package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import java.util.Set;

@Component
public class SecurityManager {
    public boolean hasEditRightsOnTraining(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getForLogin(username).orElseThrow();
        Set<Long> managedTeacherIds = user.getManagedTeacherIds();


        Training training = trainingRepo.findById(id).orElseThrow();

        return managedTeacherIds.contains(training.getTeacher().getId());
//        if (!managedTeacherIds.contains(training.getTeacher().getId())) {
//            throw new IllegalArgumentException("Ia mana !");
//        }
    }

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TrainingRepo trainingRepo;
}
