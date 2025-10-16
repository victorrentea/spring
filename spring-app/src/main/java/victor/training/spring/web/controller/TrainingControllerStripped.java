package victor.training.spring.web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

@RestController // +add this to an @Import on a @Configuration
@RequestMapping("api/trainings")
public class TrainingControllerStripped {
	@Autowired
	private TrainingService trainingService;

//  @GetMapping// GET /api/trainings
//	public List<TrainingDto> getAllTrainings() {
//		return trainingService.getAllTrainings();
//	}

  @GetMapping("{trainingId}") // GET /api/trainings/17
  public TrainingDto getTrainingById(
      @PathVariable Long trainingId/*,
      @PathVariable String ver*/) {
//    System.out.println("Ver: " + ver);
    return trainingService.getTrainingById(trainingId);
  }

  @PostMapping // POST /api/trainings {body} -> server generates the ID (DB sequence)
	public ResponseEntity<Void> createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
		var id = trainingService.createTraining(dto);

    URI urlOfNewlyCreatedResource = URI.create("/api/trainings/" + id);
    return ResponseEntity.created(urlOfNewlyCreatedResource)
        .header("your-stuff","is cool")
        .build();
	}
//  -> client generates the UUID
//  @PutMapping("{uuid}") // PUT /api/trainings/aada-afs-das-das-das-as-dc-123 {body with name, ...}
  // the server will attempt to insert this new ID in DB. fail if already there,
  // chances = 0, unless you retried. any retry will NOT create a new row, but fail with UK violation
  // ==> create becomes idempotent

  @PutMapping("{trainingId}")
	public void updateTraining(@PathVariable Long trainingId, @RequestBody @Valid TrainingDto dto) throws ParseException {
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

//  @GetMapping
//	public List<TrainingDto> search(
//      @RequestParam(required = false) String name,
//      @RequestParam(required = false) Long teacherId) {
//    TrainingSearchCriteria criteria = new TrainingSearchCriteria();
//    criteria.name = name;
//    criteria.teacherId = teacherId;

  @GetMapping // eg http://localhost:8080/api/trainings?teacherId=2
	public List<TrainingDto> search(TrainingSearchCriteria criteria) { // spring automatically maps requests query params ?name= to fields of this obj
		return trainingService.search(criteria);
	}
  // for large criteria, the long URL can be truncated > 2000 (lost) by servers on the way
  // for sensitive criteria (phonenumbers, address) -> the URL gets saved by some servers
  // ==> criteria -> BODY
}
