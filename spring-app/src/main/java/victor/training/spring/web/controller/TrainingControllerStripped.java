package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.security.config.jwt.JwtPrincipal;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/trainings")
public class TrainingControllerStripped {
	private final TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	@PostMapping
	public void createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{trainingId}")
	public void updateTraining(@PathVariable Long trainingId, @RequestBody @Validated TrainingDto dto) throws ParseException {
		dto.id = trainingId;
		trainingService.updateTraining(dto);
	}

//	@PutMapping("{trainingId}/activate") // nici o emotie
//	public void activateTraining(@PathVariable Long trainingId) {
//		trainingService.activate(trainingId);
//	}

	// TODO Allow only for role 'ADMIN'
	// TODO Fix UX
	// TODO Allow also for 'POWER' role; then remove it. => update UI but forget the BE
	// TODO Allow for authority 'training.delete'
	// TODO Allow only if the current user manages the programming language of the training
	//  (comes as 'admin_for_language' claim in in KeyCloak AccessToken)
	//  -> use SpEL: @accessController.canDeleteTraining(#id)
	//  -> hasPermission + PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")

	// ROLE-Based
//	@PreAuthorize("hasRole('ADMIN')")
//	@Secured({"ROLE_ADMIN"}) //asta daca userii au doar ROLEURI.

	// authority-based
//	@PreAuthorize("hasAuthority('training.delete')") // nu mai e prefixat automat cu ROLE_
	public void deleteTrainingById(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		JwtPrincipal jwtPrincipal = (JwtPrincipal) principal;
		String userCountryDinToken = jwtPrincipal.getCountry();
		Training training = trainingRepo.findById(id).orElseThrow();
//		if (!training.getcountry .equals(userCountryDinToken)) {
//			throw new IllegalArgumentException("na-i voie");
//		}

		trainingService.deleteById(id);
	}

	private final TrainingRepo trainingRepo;

//	@GetMapping // da-l in ma-sa de REST, vreau POST sa vad frumos pe request/ postman
	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

//	@GetMapping("search-by-name") // ?name=dasdasd
//	public List<TrainingDto> searchByName(@RequestParam String name) {
//		return trainingService.search(criteria);/**/
//	}
}
