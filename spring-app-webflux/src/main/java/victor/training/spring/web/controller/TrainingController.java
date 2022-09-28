package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public Flux<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public Mono<TrainingDto> getTrainingById(@PathVariable TrainingId id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping
	public Mono<Void> createTraining(@RequestBody  TrainingDto dto) throws ParseException {
		return trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public Mono<Training> updateTraining(@PathVariable Long id, @RequestBody @Validated(TrainingDto.ForUpdateFlow.class) TrainingDto dto) throws ParseException {
		return trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'. Then also 'POWER_USER'
	// TODO Allow for authority 'training.delete'
	// TODO a) Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	//   OR b) Allow only if the current user manages the language of that training
	// TODO @accessController.canDeleteTraining(#id)
	// TODO see PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
	public Mono<Void> deleteTrainingById(@PathVariable Long id) {
		return trainingService.deleteById(id);
	}

	// GET + body = risky
	// GET search?name=JPA&trainigId=3 ..... 2000 char max in URL + pain on client
	// POST pragmatic
	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	// Hint: try direcly @GetMapping with no @RequestBody annot

}
