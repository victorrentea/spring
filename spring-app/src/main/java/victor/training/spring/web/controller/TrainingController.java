package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;


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
	public TrainingDto getTrainingById(@PathVariable /*TrainingId*/ long id) {
		return trainingService.getTrainingById(id);
		//TODO if id is not found, return 404 status code
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
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
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}


	// TODO for searches, should we use GET or POST ?
	@GetMapping("search") // pure REST
	public List<TrainingDto> searchUsingGET(
					@RequestParam(required = false) String name,
					@RequestParam(required = false) Long teacherId) {
		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
	}
	@PostMapping("search") // pragmatic HTTP endpoints
	public List<TrainingDto> searchUsingPOST(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	//	@GetMapping("search") // OMG does the same as the @GetMapping
	public List<TrainingDto> searchUsingGET(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

}
