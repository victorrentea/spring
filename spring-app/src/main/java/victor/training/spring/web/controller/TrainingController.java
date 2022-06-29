package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
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

		TrainingDto dto = trainingService.getTrainingById(id);
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		return dto;
		//TODO if id is not found, return 404 status code
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		// TODO what if id != dto.id
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);

		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Allow for authority 'training.delete'
	// TODO Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
//	@Secured("ADMIN") // shorter if you use only ROLES
//	@PreAuthorize("hasAnyRole('ADMIN','POWER_USER')")
	@PreAuthorize("hasAuthority('training.delete') && @permissionChecker.canEditTraining(#id)")
	@DeleteMapping("{id}/delete")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}
	private final TrainingRepo trainingRepo;

	// TODO GET or POST ?
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}


@RequiredArgsConstructor
@Service
class PermissionChecker {
	private final TrainingRepo trainingRepo;

private final UserRepo userRepo;
	public boolean canEditTraining(Long trainingId) {
		Training t = trainingRepo.findById(trainingId).orElseThrow();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.getForLogin(username).orElseThrow();
		return user.getManagedTeacherIds().contains(t.getTeacher().getId());
	}
}