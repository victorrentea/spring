package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.repo.TrainingRepo;
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
	public TrainingDto getTrainingById(@PathVariable /*TrainingId*/ long id) {
//		try {
//			return ResponseEntity.ok(trainingService.getTrainingById(id));
//		} catch (NoSuchElementException e) {
//			return ResponseEntity.status(204).build();
//		}
		return trainingService.getTrainingById(id);
		//TODO if id is not found, return 404 status code
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody /*@Validated(TrainingDto.Sumbitted.class)*/ TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Allow for authority 'training.delete'
	// TODO Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
//			BOEmployee.branchId = customer.branchId
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
//	@Secured("ADMIN")
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("{id}/delete")
	public void deleteTrainingById(@PathVariable Long id) {

//		token.getList().contains(
//		trainingRepo.findById(id).get().getTeacher().getId());

		trainingService.deleteById(id);
	}

	@Autowired
	private TrainingRepo trainingRepo;


	// TODO GET or POST ?
	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	@GetMapping("search")
	public List<TrainingDto> searchGet(TrainingSearchCriteria criteria) {
		log.info("Criteria: " + criteria);
		return trainingService.search(criteria);
	}

}
