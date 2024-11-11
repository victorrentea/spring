package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/trainings")
public class TrainingControllerStripped {
  @Autowired
  private TrainingService trainingService;

  @GetMapping
  public List<TrainingDto> getAllTrainings() {
    return trainingService.getAllTrainings();
  }

  @GetMapping("{trainingId}")
  public TrainingDto getTrainingById(@PathVariable Long trainingId) {
    return trainingService.getTrainingById(trainingId);
  }

  @PostMapping
  public void createTraining(
      @RequestBody @Validated TrainingDto dto) throws ParseException {
    trainingService.createTraining(dto);
  }

  @PutMapping("{trainingId}")
  public void updateTraining(
      @PathVariable Long trainingId,
      @RequestBody @Validated TrainingDto dto) throws ParseException {
    dto.id = trainingId;
    trainingService.updateTraining(dto);
  }
  // TODO Allow only for role 'ADMIN'... or POWER or SUPER
  // TODO Allow for authority 'training.delete'
  // TODO The current user must manage the the teacher of that training
  //  	User.getManagedTeacherIds.contains(training.teacher.id)
  // TODO @accessController.canDeleteTraining(#id)
  // TODO PermissionEvaluator

  @DeleteMapping("{id}")
  public void deleteTrainingById(@PathVariable Long id) {
    trainingService.deleteById(id);
  }

  @PostMapping("search") // #traditie
  public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
    return trainingService.search(criteria);
  }

  @GetMapping("search") // GET /api/trainings/search?name=Java&date=2021-12-01
  public List<TrainingDto> searchGet(@RequestParam TrainingSearchCriteria criteria) {
    System.out.println("params cititi din ? = " + criteria);
    return trainingService.search(criteria);
  }
}
