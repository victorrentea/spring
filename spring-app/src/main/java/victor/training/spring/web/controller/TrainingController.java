package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
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
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;


@RequiredArgsConstructor
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
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		trainingService.createTraining(dto);
	}

	@PutMapping("{trainingId}")
//	@PreAuthorize("hasAuthority('training.edit') && @securityService.checkCanUpdateTraining(#trainingId)")
	public void updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto dto) throws ParseException {
		// TODO what if id != dto.id
//		securityService.checkCanUpdateTraining(trainingId);

		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
//		dto.description = dto.description.replace("<", "&lt;");
		trainingService.updateTraining(trainingId, dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Allow for authority 'training.delete'

	// TODO Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]

	//, 'POWER_USER'
//	@PreAuthorize("hasAnyRole('ADMIN')") // Spring Expression Language (SpEL)
	@PreAuthorize("hasAuthority('training.delete') && @securityService.checkCanUpdateTraining(#trainingId)")
	@DeleteMapping("{trainingId}/delete")
	public void deleteTrainingById(@PathVariable Long trainingId) {
//		securityService.checkCanUpdateTraining(trainingId);

		trainingService.deleteById(trainingId);
	}


private final SecurityService securityService;

	// TODO GET or POST ?
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

}

@Component
@RequiredArgsConstructor
class SecurityService {
	public boolean checkCanUpdateTraining(Long trainingId) {
		Training training = trainingRepo.findById(trainingId).orElseThrow();
		Long teacherId = training.getTeacher().getId();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepo.getForLogin(username).orElseThrow();
		return user.getManagedTeacherIds().contains(teacherId);
	}
	private final TrainingRepo trainingRepo;
	private final UserRepo userRepo;
}
