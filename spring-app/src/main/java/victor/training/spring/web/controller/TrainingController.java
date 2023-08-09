package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.service.TrainingService;

import java.io.IOException;
import java.util.List;

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

	@GetMapping(value = "{id}" , produces = "application/json")
//	public TrainingDto get(@PathVariable /*TrainingId*/ long id) {
	public TrainingDto get(@PathVariable TrainingId id) {
		return trainingService.getTrainingById(id.id());
		//TODO return 404 if not found
	}

	// TODO @Validated / @Valid
	@Operation(description = "Create a training")
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody TrainingDto dto) {
		long id = trainingService.createTraining(dto);
		return ResponseEntity.status(201)
				.header("Location", "blabla/" + id)
				.build();
	}

	@Operation(description = "Create a training")
	@PutMapping(value = "{trainingId}")
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

//	@GetMapping("search") // pragmatic HTTP endpoints = using POST to search
//	public List<TrainingDto> searchBold(@RequestBody TrainingSearchCriteria criteria) {}

	@PostMapping("search") // pragmatic HTTP endpoints = using POST to search
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}

	@GetMapping("search") // by-the-book REST. problem= ?
	// cannot have RequestBody (the standard allows it since 2014 but we are afraid of some old proxy that discards GET BODY requedst)
	public List<TrainingDto> searchUsingGET(
					@RequestParam(required = false) String name, // search params in the url ?
					// good to share the search with a friend
					@RequestParam(required = false) Long teacherId) {
		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
	}
	//	@GetMapping("search") // OMG does the same as the above, but it's not OpenAPI friendly
	public List<TrainingDto> searchUsingGET(TrainingSearchCriteria criteria) {
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
