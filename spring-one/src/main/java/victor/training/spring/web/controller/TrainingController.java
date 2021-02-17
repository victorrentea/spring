package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

@RestController
//@EnableJpaRepositories
@RequestMapping("/rest/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping(produces = {"application/json","application/xml","text/json"})
	// TODO [SEC] Restrict display for trainings of teachers of users
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	// TODO [SEC] Check user manages training of this training
	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO [SEC] Check user manages teacher of this training
	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'
	//  TODO ... or POWER or SUPER
	// TODO Allow for authority 'deleteTraining'
	// TODO Requirement: A training can only be deleted by user managing the teacher of that training (User.getManagedTeachedIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

	// <c:if test="user.role == 'ADMIN' || user.role='POWER'"> <<<<<< ROLE-based authorization . e ok pt app mici cu putine roluri

	// <c:if test="user.permission.canDelete">  <<<<< permission -based authorization: mai introduci un nivel de abstractie:
			// calculezi valoarea "canDelete" o singura data si o folosesti si in JSP si pe backend


//	@PreAuthorize("hasRole('ADMIN')") // Spring Expression Language SpringEL SpEL
//	@PreAuthorize("hasAuthority('deleteTraining') && @permissionChecker.checkPermission(#id)")
	@PreAuthorize("hasPermission(#id, 'TRAINING', 'WRITE')")
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	@PostMapping
	@CrossOrigin("*")
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}


//@RepositoryRestResource(path = "/api/trainings")
//interface TrainingRestRepository extends JpaRepository<Training, Long> {
//}