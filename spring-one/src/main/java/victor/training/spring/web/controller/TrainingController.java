package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Slf4j
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
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		authService.checkLevel1();

		trainingService.deleteById(id);
	}

	// TODO GET or POST ?
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

	@Autowired
	private AuthService authService;
}
@Slf4j
@Component
class AuthService {

	public void checkLevel1() {
		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>)
			SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Map<String, Object> otherClaims = keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims();
		String accessLevel = (String) otherClaims.get("DOB");
		log.warn("Accessing using level " + accessLevel);

		if (!accessLevel.equals("level-1")) {
			throw new IllegalArgumentException("NOT ALLOWED");
		}
	}
}