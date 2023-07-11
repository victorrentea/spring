package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.service.TrainingService;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

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
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "cand nu e")
	})
//	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(trainingService.getTrainingById(id));
//		} catch (NoSuchElementException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping
	public ResponseEntity<Void> createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException, URISyntaxException {
		Long id = trainingService.createTraining(dto);
		return ResponseEntity.ok()
				.location(new URI("http://localhost:8080/api/trainings/"+id ))
				.build();
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto dto) throws ParseException {
		dto.id = trainingId;
		trainingService.updateTraining(dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

	@DeleteMapping("{id}")

	// role-based authorization
//	@PreAuthorize("hasAnyRole('ADMIN','POWER')") //sau
//	@Secured("ROLE_ADMIN")

	// feature-based authorization
	@PreAuthorize("hasAuthority('training.delete')") //sau
	public void deleteTrainingById(@PathVariable Long id) {

		// TODO The current user must manage the the teacher of that training
		//  	User.getManagedTeacherIds.contains(training.teacher.id)

		Training training = trainingRepo.findById(id).orElseThrow();
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByUsernameForLogin(uname).orElseThrow();
		if (!user.getManagedTeacherIds().contains(training.getTeacher().getId())) {
//		if (infoDinToken.getRegion().equals(trainig.getRegion))
			throw new IllegalArgumentException("N-ai voie!");
		}

		trainingService.deleteById(id);
	}

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TrainingRepo trainingRepo;

//	@GetMapping("search") // GET nu are de obicei corp, pana in 2014 era ignorat
//	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {

	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
