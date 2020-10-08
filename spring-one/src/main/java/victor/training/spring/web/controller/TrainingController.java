package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.service.TrainingService;

@RestController
@RequestMapping("rest/trainings")
public class TrainingController {
   @Autowired
   private TrainingService trainingService;

   // TODO [SEC] Restrict display for trainings of teachers of users
   @GetMapping
   public List<TrainingDto> getAllTrainings() {
      return trainingService.getAllTrainings();
   }

   @GetMapping("{id}")
   // TODO [SEC] Check user manages training of this training
   public TrainingDto getTrainingById(@PathVariable Long id) {
      return trainingService.getTrainingById(id);
   }

   // TODO [SEC] Check user manages teacher of this training
   @PutMapping("{id}")
   public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
      trainingService.updateTraining(id, dto);
   }

   // after switching to DatabaseUserDetailsService
   // TODO [SEC] 1 Allow only for ROLE 'USER'
   // TODO [SEC] 2 Authorize the user to have the authority 'deleteTraining'
   // TODO and @accessController.canDeleteTraining(#id)

   /**
    * @see victor.training.spring.web.domain.UserProfile
    */
   @DeleteMapping("{id}")
   @PreAuthorize("hasRole('ADMIN')") // anotation-based authorization.
   public void deleteTrainingById(@PathVariable Long id) {
      Training training = trainingRepo.findById(id).orElseThrow(() -> new RuntimeException("Draga hackere, n-ai nimerit id-ul. Mai baga o fisa!?"));

      Long teacherId = training.getTeacher().getId();
      String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

      User user = userRepo.findByUsername(currentUsername);

      if (!user.getManagedTeacherIds().contains(teacherId)) {
         throw new IllegalArgumentException("N-ai voie!");
      }

      trainingService.deleteById(id);
   }

   @Autowired
   private TrainingRepo trainingRepo;
   @Autowired
   private UserRepo userRepo;



   @PostMapping
   public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
      trainingService.createTraining(dto);
   }
}
