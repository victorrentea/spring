package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.service.TrainingService;

import javax.validation.Valid;
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
	public TrainingDto getTrainingById(@PathVariable TrainingId /*long*/ id) {
		return trainingService.getTrainingById(id.id());
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		// TODO what if id != dto.id
//		if (!Objects.equals(dto.id, id)) {
//			throw new IllegalArgumentException();
//		}
		dto.id = id;
		trainingService.updateTraining(dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Allow for authority 'training.delete'
	// TODO Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")

	// role mode
//	@Secured("ADMIN")
//	@PreAuthorize("hasAnyRole('ADMIN','POWER_USER','ADMIN_SPITAL')")

	//authority model (mai fine grained)
	@PreAuthorize("hasAuthority('ROLE_training.delete')")

	// data security
//	@PreAuthorize("@permissionGranter.areVoiePeTraininguId(#id)")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	// TODO GET or POST ?
	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

}

@Component
class PermissionGranter {
	public boolean areVoiePeTraininguId(long id) {
		return true; // SELECT sa vezi daca ai voie. daca poacientu e din eruopa
	}
}
