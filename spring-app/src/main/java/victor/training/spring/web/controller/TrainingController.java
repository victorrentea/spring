package victor.training.spring.web.controller;

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

import javax.validation.Valid;
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
		return trainingService.getTrainingById(id);
		//TODO if id is not found, return 404 status code
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		dto.id =  id;
		trainingService.updateTraining(dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Fix UX
	// TODO Allow also 'POWER', si inca 3 roluri; then remove it. => OWASP top 10
	// TODO Allow for authority 'training.delete'
	// TODO a) Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	//   OR b) Allow only if the current user manages the language of that training
	// TODO @accessController.canDeleteTraining(#id)
	// TODO see PermissionEvaluator [GEEK]

	// TODO GET or POST ?
//	@GetMapping("search")// are limitari pentru ca GET n-avea body pana acum ; din 2014 se poate, dar e inca riscant
	// pana cand GET cu body e suportat, >
	@PostMapping("search")// hai siktir Fielding
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	// Hint: try direcly @GetMapping with no @RequestBody annot


	@DeleteMapping("{id}")
//	@PreAuthorize("hasAnyRole('ADMIN','POWER')")
	@PreAuthorize("hasAnyRole('ADMIN','POWER') && @securityService.canChangeTraining(#id)") // Spring Expression Language (SpEL)
//	@PreAuthorize("hasPermission(#id, 'training','WRITE')") // Spring Expression Language (SpEL)

	//	@Secured("ADMIN") // mai scurt dar mai putin flexibila
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	@Autowired
	private SecurityService securityService;
}

@Component
class SecurityService {
	public boolean canChangeTraining(Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.getForLogin(username).get();
		Set<Long> managedTeacherIds = user.getManagedTeacherIds();
		Training training = trainingRepo.findById(id).orElseThrow();
		Long teacherId = training.getTeacher().getId();
		return managedTeacherIds.contains(teacherId);
	}

	@Autowired
	private TrainingRepo trainingRepo;
	@Autowired
	private UserRepo userRepo;

}