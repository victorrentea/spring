package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingDto.ValidationGroup.UpdateFlow;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/trainings")
//@PreAuthorize("hasRole('ADMIN')")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{trainingId}")
	public TrainingDto getTrainingById(@PathVariable Long trainingId) {
		return trainingService.getTrainingById(trainingId);
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id,@Validated(UpdateFlow.class) @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

//	@RolesAllowed("ROLE_ADMIN")
//@PreAuthorize("hasRole('ADMIN')") // the best
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
//		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities())
		trainingService.deleteById(id);
	}

	@PostMapping("search") // for technical reasons
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
