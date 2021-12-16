package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.service.TrainingService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	@Autowired
	private SecurityService securityService;
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {

		Optional<TrainingDto> dtoOpt = trainingService.getTrainingById(id);
		if (!dtoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dtoOpt.get());
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
//		dto.id = null; // lenient, hai ca ne intelege noi
//		if (dto.id != null) throw new IllegalArgumentException("Draga developer, nu-mi dai tu id-uri la creere"); // naspa
		trainingService.createTraining(dto);
	}

	@PutMapping ("{id}")// = OVERWRITE
	@PreAuthorize("hasRole('ADMIN')")
	public void updateTraining(
		@PathVariable Long id,
		@RequestBody  @Valid TrainingDto dto) throws ParseException {
//		dto.id = id; // lenient, hai ca ne intelege noi
//		if (!dto.id.equals(id)) throw new IllegalArgumentException("Draga developer, id-urile tre sa fie egala"); // naspa
		securityService.checkPermissionOnTraining(id);
		trainingService.updateTraining(id, dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'SUPPORT')")
	@PreAuthorize("hasAuthority('training.delete')")
	@DeleteMapping("{id}/delete")
	public void deleteTrainingById(@PathVariable Long id) {
		securityService.checkPermissionOnTraining(id);

		trainingService.deleteById(id);
	}

	private final TrainingRepo trainingRepo;

	@PostMapping("search") // ar treb GET dar din motive tehnice (ca vreau sa trimit criteriile JSON in body, fac POST)
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	// daca expui endpointul de search pe net si-l vad si altii
//	@GetMapping("search") // ar treb GET dar din motive tehnice (ca vreau sa trimit criteriile JSON in body, fac POST)
////	public List<TrainingDto> searchUrl(@RequestMapping(required=false) String name, @RequestMapping(required=false) Long teacherId ) { // +un type mapper de spring care sa culeaga din url de request param de query
//	public List<TrainingDto> searchUrl(TrainingSearchCriteria criteria) { // +un type mapper de spring care sa culeaga din url de request param de query
//		// practic urul arata ?name=bla&teacherId=19
//		return trainingService.search(criteria);
//	}
}
