package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.service.TrainingService;

import javax.annotation.security.RunAs;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
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
	@GetMapping(value = "search",produces = "application/json") // OMG does the same as the @GetMapping
	public List<TrainingDto> searchUsingGET(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

	@GetMapping(value = "search-export-pdf",produces = "application/pdf") // OMG does the same as the @GetMapping
	public void searchUsingGETPdf(TrainingSearchCriteria criteria, HttpServletResponse response, HttpServletRequest request) {
//		request.getHeader()
//		response.heade // "Content-Disposition" header
//		response.getOutputStream() // pour generated PDF bytes here
	}

	// TODO for searches, should we use GET or POST ?
//	@GetMapping("search") // pure REST
//	public List<TrainingDto> searchUsingGET(
//					@RequestParam(required = false) String name,
//					@RequestParam(required = false) Long teacherId) {
//		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
//	}
}
