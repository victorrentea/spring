package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/trainings")
public class TrainingControllerStripped {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
		// if the training is not found by ID return 404 not 500
		try {
			return ResponseEntity.ok(trainingService.getTrainingById(id));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).build();
		}
	}

	@PostMapping
	public void createTraining(@Validated @RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody @Validated TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
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




	// POST or GET ?
//	@GetMapping + body // https://stackoverflow.com/questions/978061/http-get-with-request-body
//	@GetMapping // w/o body means all parameters come as search?name=this%20and%20that&trainingId=6 => 🤢
	@PostMapping("search") // not really standard REST, but pragmatic HTTP endpoint
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
