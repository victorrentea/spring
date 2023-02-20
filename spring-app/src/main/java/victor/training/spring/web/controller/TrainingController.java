package victor.training.spring.web.controller;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
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
		TrainingDto dto = trainingService.getTrainingById(id);
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		return dto;
		//TODO if id is not found, return 404 status code
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Fix UX
	// TODO Allow also 'POWER'; then remove it. => OWASP top 10
	// TODO Allow for authority 'training.delete'
	// TODO a) Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	//   OR b) Allow only if the current user manages the language of that training
	// TODO @accessController.canDeleteTraining(#id)
	// TODO see PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}


	// TODO for searches, should we use GET or POST ?

	@PostMapping("search")
	public List<TrainingDto> searchUsingPOST(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	@GetMapping("search")
	public List<TrainingDto> searchUsingGET(
					@RequestParam(required = false) String name,
					@RequestParam(required = false) Long teacherId) {
		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
	}
	//	@GetMapping("search") // OMG does the same as the above one
	public List<TrainingDto> searchUsingGET(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

}
