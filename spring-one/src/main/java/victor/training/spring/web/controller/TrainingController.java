package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/trainings")  // @Resource("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping // @GET @Resource("api/trainings")
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}
	@GetMapping ("by-name") // api/trainings/by-name?name=ceva
	public List<TrainingDto> getTrainingsByName( @RequestParam String name) {
		System.out.println("Query by name " + name);
		return Collections.emptyList();
	}

	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable long id) {
		return trainingService.getTrainingById(id);
	}

	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}/start-date")
	public void updateTrainingStartDate(@PathVariable Long id, @RequestBody Date date) {
		trainingService.updateTrainingStartDate(id, date);
	}
	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO Requirement: The current user manages the the teacher of that training (User.getManagedTeacherIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	// TODO
	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	@PostMapping("list")
	public List<String> search(@RequestBody List<String> strings) {
		return strings.stream().map(String::toUpperCase).collect(Collectors.toList());
	}
}
