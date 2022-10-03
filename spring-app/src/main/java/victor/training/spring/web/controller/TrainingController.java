package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.aspects.Logged;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Logged
@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	private final TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	// asa nu:
//	@GetMapping("{id}")
//	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable TrainingId id) {
//		try {
//			return ResponseEntity.ok(trainingService.getTrainingById(id));
//		} catch (NoSuchElementException e) {
//			return ResponseEntity.status(404)
////					.header("Ceva", "frumos💐")
//					.build();
//		}
//	}
	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable TrainingId id) {
		return trainingService.getTrainingById(id);
	}
	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException {
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
	public void updateTraining(@PathVariable Long id,  @Validated @RequestBody TrainingDto dto) throws ParseException {
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

	//3: (fitza) GET BODY de request
	//1: @PostMapping("search") // pragmatic, pe Roy, da-l in masa
	//2: GET ?a=1&b=c
	@GetMapping("search") // merge sa ia automat din ?  bla=&bla=...
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	// Hint: try direcly @GetMapping with no @RequestBody annot

}
