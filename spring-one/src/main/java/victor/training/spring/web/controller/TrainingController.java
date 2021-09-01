package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.DuplicatedTrainingException;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	@PreAuthorize("hasRole('user')")
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@Validated @RequestBody TrainingDto dto) throws ParseException {
//		try {
			trainingService.createTraining(dto);
//			return ResponseEntity.status(200).build();
//		} catch (DuplicatedTrainingException e) {
//			return ResponseEntity.status(404).build();
//		}
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		try {
			trainingService.updateTraining(id, dto);
		} catch (DuplicatedTrainingException e) {
			// hide
		}
	}

	@PutMapping("{id}/description") // ideal for changing one field.
	public void updateTrainingDescription(@PathVariable Long id, @RequestBody String newDescription) throws ParseException {
		// TODO
	}

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
