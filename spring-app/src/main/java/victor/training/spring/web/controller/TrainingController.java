package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.service.TrainingService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	private final TrainingService trainingService;
  private final RestTemplateBuilder restTemplateBuilder;

  @GetMapping
	public List<TrainingDto> getAll() {
		return trainingService.getAllTrainings();
	}

//	@GetMapping("{id}")
//	public ResponseEntity<TrainingDto> get(@PathVariable /*TrainingId*/ long id) {
//    try {
//      return ResponseEntity.ok(trainingService.getTrainingById(id));
//    } catch (NoSuchElementException e) { // REJECT la CR: "daca sare si din alte endpointuri, daca uiti sa dai copy-paste? Si-n plus, e noise (gunoi)"
//      return ResponseEntity.notFound().build();
//    }
//	}

  // ... waiting for Valhalla project
  record TrainingId(long id) {} // fights "primitive obsession" code smell: nu mai ai cum sa uiti ca acest long e un id de training.
  // acest string e un CNP, IBAN
	@GetMapping("{id}")
//	public TrainingDto get(@PathVariable TrainingId id) { // vis!
	public TrainingDto get(@PathVariable  long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO @Validated / @Valid
	@Operation(description = "Create a training") // doc de swagger
	@PostMapping
	public void create(@RequestBody @Validated TrainingDto dto) {
		trainingService.createTraining(dto);
	}

	@Operation(description = "Create a training")
	@PutMapping("{trainingId}")
	public void update(@PathVariable Long trainingId, @RequestBody TrainingDto dto) {
		dto.id = trainingId;
		trainingService.updateTraining(dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Fix UX
	// TODO Allow also for 'POWER' role; then remove it. => update UI but forget the BE
	// TODO Allow for authority 'training.delete'
	// TODO Allow only if the current user manages the programming language of the training
	//  (comes as 'admin_for_language' claim in in KeyCloak AccessToken)
	//  -> use SpEL: @accessController.canDeleteTraining(#id)
	//  -> hasPermission + PermissionEvaluator [GEEK]
	@DeleteMapping("{trainingId}")
	public void delete(@PathVariable Long trainingId) {
		trainingService.deleteById(trainingId);
	}

	private final TrainingRepo trainingRepo;


	// --------------------------------------------------
	// TODO 'search' should use GET or POST ?
	// GET:
	// POST:

	@PostMapping("search") // pragmatic HTTP endpoints
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

	@GetMapping("search") // by-the-book REST
	public List<TrainingDto> searchUsingGET(
					@RequestParam(required = false) String name,
					@RequestParam(required = false) Long teacherId) {
		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
	}
  @GetMapping("search/body") // OMG does the same as the above, but it's not OpenAPI friendly
	public List<TrainingDto> searchUsingGET(@RequestBody TrainingSearchCriteria criteria) {
    // DON'T
		return trainingService.search(criteria);
	}


  @Value("classpath:/static/spa.jpg")
  private Resource picture;
	@GetMapping("download")
	public ResponseEntity<Resource> downloadFile() throws IOException {
		return ResponseEntity.ok()
						.contentType(IMAGE_JPEG)
						.contentLength(picture.contentLength())
//						.header("Content-Disposition", "attachment; filename=\"spa.jpg\"") // tells browser to download as file
						.body(picture);
	}

}
