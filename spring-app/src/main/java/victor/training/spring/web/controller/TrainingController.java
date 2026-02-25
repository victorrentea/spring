package victor.training.spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/trainings")
public class TrainingController {
  private final TrainingService trainingService;

  public TrainingController(TrainingService trainingService) {
    this.trainingService = trainingService;
  }

  @GetMapping
  public List<TrainingDto> getAllTrainings() {
    return trainingService.getAllTrainings();
  }

  // GET /api/trainings/1
  @GetMapping(value = "{id}"/*,produces = "application/json"*/)// reject requests with Accept: application/json
  public TrainingDto getTrainingById(@PathVariable Long id) {
    return trainingService.getTrainingById(id);
  }

  @PostMapping
  public ResponseEntity<Long> createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException {
    // 201 responses should include a Location: response header
    var id = trainingService.createTraining(dto);
//    return ResponseEntity.created(URI.create("/api/trainings/" + id)).body(id);
    //same as
    return ResponseEntity.status(HttpStatus.CREATED)
        .header("Location", "/api/trainings/" + id)
        .body(id);
  }

  @PutMapping("{trainingId}")
  public void updateTraining(@PathVariable Long trainingId,
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

  // GET /api/trainings/search?name=bla&teacherId=5

  @GetMapping("search")
//  public List<TrainingDto> search(@RequestParam String name, @RequestParam Long teacherId) {
//    return trainingService.search(new TrainingSearchCriteria(name, teacherId));
//  }

  // the query params are automatically mapped to the record fields by Spring, as long as the names match
  public List<TrainingDto> search(TrainingSearchCriteria criteria) {
    return trainingService.search(criteria);
  }
}
