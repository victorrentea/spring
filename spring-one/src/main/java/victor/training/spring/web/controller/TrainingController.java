package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import javax.validation.Valid;

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
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}") // == overwrite  (idempotent)
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	@PutMapping("{id}/name") // == overwrite  (idempotent)
	public void updateTrainingName(@PathVariable Long id, @RequestBody String newName) throws ParseException {
		// TODO
	}

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.edit'
	// TODO Requirement: The current user manages the the teacher of that training (User.getManagedTeacherIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

//	@PostMapping("") // create a email with status = TO_SEND
//	public void sendEmail() {
//	}

	// TODO
//	@GetMapping("")  // ?name=bla&teascherId=13
	@PostMapping("search")
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
