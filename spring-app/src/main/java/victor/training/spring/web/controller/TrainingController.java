package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.aspects.Logged;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@Logged
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
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		alta();
		trainingService.createTraining(dto);
	}

//	@Scheduled(fixedDelay = 60*1000) // rau ca ce: doua pod-uri ar putea
//	// simultan sa incerce acelasi rand NOT DONE
//	public void laFiecareMinutIaDinBazaSiIncearcaANAF() {
//		call anaf
//		daca ok marchez in DB status=DONE
//	}
	// mai bine inloc de scheduled, faci un endpoint si-l chemi o data pe
	// clusterul tau la ora potrivita (din exterior)
	// daca vrei scheduling pe cloud > shedlock: https://www.baeldung.com/shedlock-spring

	public void alta() {
		System.out.println("Alta functie: o functie chemata in acceeasi clasa NU poate fi" +
						   " interceptata de SPring AOP");
	}
	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'. Then also 'POWER_USER'
	// TODO Allow for authority 'training.delete'
	// TODO a) Allow only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	//   OR b) Allow only if the current user manages the language of that training
	// TODO @accessController.canDeleteTraining(#id)
	// TODO see PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	// TODO GET or POST ?
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	// Hint: try direcly @GetMapping with no @RequestBody annot

}
