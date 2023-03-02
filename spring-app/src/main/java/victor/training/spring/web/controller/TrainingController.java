package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.service.TrainingService;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;


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

//	@GetMapping("{id}")
//	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable TrainingId id) {
//		try {
//			// ResponseEntity to : set status or add a header.
//			return ResponseEntity.ok(trainingService.getTrainingById(id));
//		} catch (NoSuchElementException e) {
//			return ResponseEntity.notFound().build();
//		}
//		//TODO if id is not found, return 404 status code
//	}
	@GetMapping("{id}")
	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable TrainingId id) {
		return trainingService.getTrainingById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		//TODO if id is not found, return 404 status code
	}

//	@GetMapping("{id}")
//	public TrainingDto getTrainingById(@PathVariable TrainingId id) {
//		return trainingService.getTrainingById(id);
//	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
//		throw new NullPointerException();
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable long id, @Validated @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Fix UX
	// TODO Allow also for 'POWER' role; then remove it. => update UI but forget the BE
	// TODO Allow for authority 'training.delete'
	// TODO Allow only if the current user manages the programming language of the training
	// TODO @accessController.canDeleteTraining(#id)
	// TODO see PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}



	// oh yes. the search.
	// what is the correct HTTP verb to use for search

	// GET = get (purist approach), correct but people don't expeect GET to carry a JSON body
	// POST = "create a resource"
	@PostMapping("search") // pragmatic HTTP endpoints
	public List<TrainingDto> searchUsingPOST(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	@GetMapping("search") // OMG does the same as the @GetMapping
	public List<TrainingDto> searchUsingGET(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

	// TODO for searches, should we use GET or POST ?
//	@GetMapping("search") // pure REST
//	public List<TrainingDto> searchUsingGET(
//					@RequestParam(required = false) String name,
//					@RequestParam(required = false) Long teacherId) {
//		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
//	}
}
