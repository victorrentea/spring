package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingDto.ValidationGroup.UpdateFlow;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
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
//	@PreAuthorize("hasRole('ADMIN')") // the best
	@PreAuthorize("hasRole('training.edit')")


	public void updateTraining(@PathVariable Long id,@Validated(UpdateFlow.class) @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)

	// DaTA-security: you are not allowed to delete a training unless you are a manager for the trainer doing the training


	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

//	@RolesAllowed("ROLE_ADMIN")

//	@PreAuthorize("hasRole('ADMIN')") // the best
//	@PreAuthorize("hasAnyRole('ADMIN', 'POWER_USER', 'SUPERWOMAN')") // the weakness of the role-based authorization >
	@PreAuthorize("hasRole('training.delete')") // the weakness of the role-based authorization >
	// DRY violation : repeat this list in the ui button
	@DeleteMapping("{id}")

	// ACL - Access COntrol LIst

	public void deleteTrainingById(@PathVariable Long id) {
		permissionManager.checkCanManageTraining(id);

		trainingService.deleteById(id);
	}

	@Autowired
	private PermissionManager permissionManager;


	@PostMapping("search") // for technical reasons
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}

@RequiredArgsConstructor
@Component
class PermissionManager {
	private final TrainingRepo trainingRepo;
	private final UserRepo userRepo;

	public void checkCanManageTraining(Long trainingId) {
		Training training = trainingRepo.findById(trainingId).get();
		Long teacherId = training.getTeacher().getId();

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepo.getForLogin(currentUsername).get();

		if (!user.getManagedTeacherIds().contains(teacherId)) {
			throw new IllegalArgumentException("NOT ALLOWED");
		}
	}

}