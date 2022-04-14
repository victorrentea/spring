package victor.training.spring.web.controller;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;
import java.util.Set;


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
//		if () {
//			dto.startDate = null;
//		}
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
	@PreAuthorize("hasAuthority('training.edit') and @featureAuthorizer.canUserChangeTraining(#id)")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		// TODO what if id != dto.id
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);

		trainingService.updateTraining(id, dto);
	}

//	@Secured("ROLE_ADMIN") //add the ROLE_ prefix
	// TODO Allow only for role 'ADMIN'
	// TODO Allow for authority 'training.delete'
	// TODO Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
//	@PreAuthorize("hasAnyRole('ADMIN')")

//	@PreAuthorize("hasPermission(#id, 'TRAINING', 'WRITE')")
	@PreAuthorize("hasAuthority('training.delete') and @featureAuthorizer.canUserChangeTraining(#id)")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	// TODO GET or POST ?
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

}

@Component
class FeatureAuthorizer {
	@Autowired
	private TrainingRepo trainingRepo;

	public boolean canUserChangeTraining(Long trainingId) {
		Training training = trainingRepo.findById(trainingId).orElseThrow();
		Long teacherId = training.getTeacher().getId();

		SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<Long> managedTeacherIds = securityUser.getManagedTeacherIds();
		return managedTeacherIds.contains(teacherId);
	}


}