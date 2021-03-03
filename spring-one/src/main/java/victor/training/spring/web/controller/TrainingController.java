package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.TrainingService;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/rest/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	// Nu recomand decat pentru API-uri expuse catre echipe straine
//	@GetMapping("{id}")
//	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
//		Optional<TrainingDto> dtoOpt = trainingService.getTrainingById(id);
//
//		if (!dtoOpt.isPresent()) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(dtoOpt.get());
//	}
	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id).get();
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.edit'
	// TODO Requirement: The current user manages the the teacher of that training (User.getManagedTeacherIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator
	@DeleteMapping("{id}")
//	@RolesAllowed("ADMIN")
//	@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAuthority('training.edit') and @securityAccessService.canDeleteTraining(#id)")
	public void deleteTrainingById(@PathVariable Long id) {
//		if (!securityAccessService.canDeleteTraining(id)) {
//			throw new IllegalArgumentException("n-ai voie");
//		}

		trainingService.deleteById(id);
	}
	@Autowired
	SecurityAccessService securityAccessService;

	@PostMapping
	public void createTraining(@RequestBody	 TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}
@Component
class SecurityAccessService {
	@Autowired
	TrainingRepo trainingRepo;

	public boolean canDeleteTraining(Long id) {

		SecurityUser securityUserOnSession = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<Long> managedTeacherIds = securityUserOnSession.getManagedTeacherIds();

		Training training = trainingRepo.findById(id).get();
		Long teacherId = training.getTeacher().getId();

		return managedTeacherIds.contains(teacherId);
	}
}