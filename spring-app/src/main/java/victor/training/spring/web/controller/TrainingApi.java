package victor.training.spring.web.controller;

import jakarta.validation.Valid;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/trainings")
public class TrainingApi {
	@Autowired
	private TrainingService trainingService;

	// TODO (in backlog la #123): ar trebui cfeate obiecte separate pt fiecare endpoint
//	public record GetTrainingResponse()
	record GetAllTrainingsResponse(List<TrainingDto> trainings) {}
  @GetMapping
	public GetAllTrainingsResponse getAllTrainings() {
		return new GetAllTrainingsResponse(trainingService.getAllTrainings());
	}

	// NICIODATA
//	@GetMapping("{id}")
//	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
//		try{
//			return ResponseEntity.ok(trainingService.getTrainingById(id));
//		}catch (NoSuchElementException e){
//			return ResponseEntity.notFound().build();
////			return ResponseEntity.status(404).body("Nu-i");
//		}
//	}
	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

//	@PutMapping("{uuid}") // crea cu PUT daca clientul ne da UUIDul noului record.
	// ca sa poata da clientul RETRY => face create idempotent
	@PostMapping
	public ResponseEntity<Long> createTraining(@Validated @RequestBody TrainingDto dto) throws ParseException {
//		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
//		dto.description = sanitizer.sanitize(dto.description);

		Long id = trainingService.createTraining(dto);
		return ResponseEntity.created(URI.create("http://localhost:8080/api/trainings/"+id))
				.body(id);
	}

	// INCEARCA SA #RESIST si sa nu intorci date pe response de PUT
	@PutMapping("{trainingId}")
	public void updateTraining(@PathVariable Long trainingId, @RequestBody @Validated TrainingDto dto) throws ParseException {
		// a) vine id si in URL ../13 si in body {id:13. = _ _ _
		// daca difera sau lipseste unu, ce faci?
		// a1) THROW: enerveaza
		// a2) LENIENT: il iei p-ala din path: surprinde
		// b) payloadul nu ar include ID
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
	public void deleteTrainingById(Long id) {
		trainingService.deleteById(id);
	}

	//
//	@GetMapping("search") // + BODY periculos pe internet
//	@PostMapping("search") // + @RequestBody
	@GetMapping("search") // http://localhost:8080/api/trainings/search?name=Java&teacherId=2
 	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
