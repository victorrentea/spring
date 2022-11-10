package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingControllerStripped {
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

	@PostMapping
	public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	// @Aspect ....
	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody @Valid TrainingDto dto) throws ParseException {
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

//	@GetMapping // nu GetMapping pentru ca asta cere toti param sa vina in url ?name=asda%40sdaf&teacherId=1&
	@PostMapping("search") // de ce nu POST? => nu creezi nimic, ci doar obtii data, nu e HTTP/REST pur
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
