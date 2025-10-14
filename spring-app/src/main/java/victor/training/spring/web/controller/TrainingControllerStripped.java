package victor.training.spring.web.controller;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/trainings")
public class TrainingControllerStripped {
  private final TrainingService trainingService;

  public TrainingControllerStripped(TrainingService trainingService) {
    this.trainingService = trainingService;
  }

  @GetMapping
  @Timed // http://localhost:8080/actuator/prometheus
  // sa pui si TimedAspect ca @Bean
  public List<TrainingDto> getAllTrainings() {
    log.info("(2)Loading...");
//    if (true) throw new RuntimeException("BUG🐞");
    return trainingService.getAllTrainings();
  }

  @GetMapping("{id}")
  public TrainingDto getTrainingById(@PathVariable Long id) {
    return trainingService.getTrainingById(id);
  }

  @PostMapping
  public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
    trainingService.createTraining(dto);
  }

  @PutMapping("{id}") // eg PUT /api/trainings/17
  public void updateTraining(@PathVariable Long id, @RequestBody @Valid TrainingDto dto) throws ParseException {
    dto.id = id;
    trainingService.updateTraining(dto);
  }
  // TODO Allow only for role 'ADMIN'... or POWER or SUPER
  // TODO Allow for authority 'training.delete'
  // TODO The current user must manage the the teacher of that training
  //  	User.getManagedTeacherIds.contains(training.teacher.id)
  // TODO @accessController.canDeleteTraining(#id)
  // TODO PermissionEvaluator

  @Operation(description = "Stergi cursu, duh!")// http://localhost:8080/v3/api-docs
  @DeleteMapping("{id}") // IoC
  @Secured("ROLE_ADMIN")
  public void deleteTrainingById(@PathVariable Long id) {
    trainingService.deleteById(id);
  }

//  @PostMapping("search") // http://localhost:8080/api/trainings/search + {name="...}
//  public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {

//  public List<TrainingDto> search(@RequestParam String name, @RequestParam Long teacherId) {
  @GetMapping("search") // http://localhost:8080/api/trainings/search?name=J&teacherId=1
  public List<TrainingDto> search(TrainingSearchCriteria criteria) {
    return trainingService.search(criteria);
  }
}
