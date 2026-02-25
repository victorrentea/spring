package victor.training.spring.web.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.DuplicateTrainingNameException;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("api/trainings")
public class TrainingController {
  private static final Logger log = LoggerFactory.getLogger(TrainingController.class);
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
  public ResponseEntity<Long> createTraining(
      @RequestBody @Validated/*(CREATE.class)*/ TrainingDto dto) throws ParseException {
//      @RequestBody @Validated CreateTrainingRequestDto❤️ dto) throws ParseException {
    // 201 responses should include a Location: response header
    var id = trainingService.createTraining(dto);
//    return ResponseEntity.created(URI.create("/api/trainings/" + id)).body(id);
    //same as
    return ResponseEntity.status(HttpStatus.CREATED)
        .header("Location", "/api/trainings/" + id)
        .body(id);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(DuplicateTrainingNameException.class) // attempted first, as the exception is more specific than 'Exception' above
  public String noDuplicateTrainingNameException() {
    log.info("in my special handler for DuplicateTrainingNameException");//❤️
    return "DuplicateTrainingNameException";
  }

  @PutMapping("{trainingId}")
  public void updateTraining(@PathVariable Long trainingId,
                             @RequestBody @Validated/*(UPDATE.class)*/ TrainingDto dto) throws ParseException {
//                             @RequestBody @Valid (jakarta standard) TrainingDto dto) throws ParseException {
//                             @RequestBody @Validated UpdateTrainingRequestDto❤️ dto) throws ParseException {
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
