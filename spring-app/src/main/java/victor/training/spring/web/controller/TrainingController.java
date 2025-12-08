package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
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

	@GetMapping
	public List<TrainingDto> getAll() {
		return trainingService.getAllTrainings();
	}

//  @GetMapping("{id}")
//  public ResponseEntity<TrainingDto> get( // asa nu!❌ exceptiile se prind global
//      @PathVariable /*TrainingId*/ long id) {
//    try {
//      return ResponseEntity.ok(trainingService.getTrainingById(id));
//    } catch (NoSuchElementException e) {
//      return ResponseEntity.notFound().build();
//    }
//  }


  @GetMapping("{id}")
	public TrainingDto get(@PathVariable long id) {
    return trainingService.getTrainingById(id);
	}

	// TODO @Validated / @Valid
	@Operation(description = "Create a training. Imi pasa de API clienti (alti BE) #respect")
	@PostMapping
	public void create(@RequestBody TrainingDto dto) {
		trainingService.createTraining(dto);
	}

	@Operation(description = "Update a training")
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


	// === Search ===
	// TODO 'search' should use GET✅ or POST ?
	// GET 'query' ❤️❤️❤️
	// POST daca
  // a) prea multe criterii > 2000 ch
  // b) criterii senzitive (GDPR): eg ?phone=2038504857&accountNumber=X

  // == #1 POST ==
	@PostMapping("search") // traditional
  // 😊 hides sensitive search criteria from URL eg ?phone=0158135767
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

  // == #2 GET + query params:
  // http://localhost:8080/api/trainings/search?name=J
  // 😊 users can send search URLs to friends
  // 🙁 but url <= 2000 characters
	@GetMapping("search") // traditional
	public List<TrainingDto> searchUsingGET(
					@RequestParam(required = false) String name,
					@RequestParam(required = false) Long teacherId) {
		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
	}

  // GET + query params, but captured as a DTO
  @GetMapping("search2")
  // http://localhost:8080/api/trainings/search2?name=J
	public List<TrainingDto> searchUsingGET(
      TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

  // ❌ GET + Body avoid - not fully supported
  @GetMapping("searchGetBody")
	public List<TrainingDto> searchUsingGETBody(
      @RequestBody TrainingSearchCriteria criteria) {
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
