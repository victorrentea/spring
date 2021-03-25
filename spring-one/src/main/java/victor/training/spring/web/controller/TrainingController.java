package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

//	@RequestMapping(method = RequestMethod.GET, value = "api/trainings")
	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		TrainingDto training = trainingService.getTrainingById(id);
//		if (training == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(training);
		return training;
	}

	@PostMapping
	// TODO @Valid
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

//	@PostMapping("{id}/topics") -- add children

	@PutMapping("{id}") // PUT should be idempotent -> doing it twice has no additiona effect.
	public void updateTraining(@PathVariable Long id,@RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
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

	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
