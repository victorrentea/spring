package victor.training.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/trainings")
public class TrainingControllerStripped {
  @Autowired
  private TrainingService trainingService;

  @GetMapping
  public List<TrainingDto> getAllTrainings() {
    return trainingService.getAllTrainings();
  }

  @GetMapping("/{id}")
  public TrainingDto getTrainingById(@PathVariable Long id) {
    return trainingService.getTrainingById(id);
  }

  @PostMapping
  public void createTraining( @Valid @RequestBody TrainingDto dto) throws ParseException {
    trainingService.createTraining(dto);
  }

  @PutMapping("/{id}")
  public void updateTraining(@PathVariable Long id, @Valid @RequestBody TrainingDto dto) throws ParseException {
    dto.id = id;
    trainingService.updateTraining(dto);
  }
  // TODO Allow only for role 'ADMIN'... or POWER or SUPER
  // TODO Allow for authority 'training.delete'
  // TODO The current user must manage the the teacher of that training
  //  	User.getManagedTeacherIds.contains(training.teacher.id)
  // TODO @accessController.canDeleteTraining(#id)
  // TODO PermissionEvaluator

//  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//  @PreAuthorize("hasRole('ADMIN')") // rolul in spring este implicit prefixat cu "ROLE_" < vine din 1998
  @Secured({"ROLE_ADMIN","ROLE_POWER"}) // "sau" intre
  @DeleteMapping("/{id}")
  public void deleteTrainingById(@PathVariable Long id) {
    trainingService.deleteById(id);
  }




//  public void deleteTrainingById(@PathVariable TrainingId id) { copilot stie
  record TrainingId(long id) {} // peste tot in loc de Long

  @PostMapping("/search")
  public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
    return trainingService.search(criteria);
  }

  // localhost:8080/api/trainings/search-get?name=J
  @GetMapping("/search-get")
  public List<TrainingDto> searchCuGet(TrainingSearchCriteria criteria) {
    return trainingService.search(criteria);
  }
}
