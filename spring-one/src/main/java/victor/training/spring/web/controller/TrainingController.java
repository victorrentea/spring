package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
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
      trainingService.deleteById(id);
   }

   @PostMapping
   public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
      trainingService.createTraining(dto);
   }
}
