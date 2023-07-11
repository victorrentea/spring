package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "cand nu e")
	})
//	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(trainingService.getTrainingById(id));
//		} catch (NoSuchElementException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping
	public ResponseEntity<Void> createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException, URISyntaxException {
		Long id = trainingService.createTraining(dto);
		return ResponseEntity.ok()
				.location(new URI("http://localhost:8080/api/trainings/"+id ))
				.build();
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto dto) throws ParseException {
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

//	@GetMapping("search") // GET nu are de obicei corp, pana in 2014 era ignorat
//	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {

	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
