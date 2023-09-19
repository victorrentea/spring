package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
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

	@GetMapping("{id}")
	public TrainingDto get(@PathVariable /*TrainingId*/ long id) {
		TrainingDto dto = trainingService.getTrainingById(id);
		// defense in depth? daca mi-a hackuit deja DB si poate pune ce vrea in ea
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		return dto;
		//TODO return 404 if not found
	}

	// TODO @Validated / @Valid
	@Operation(description = "Create a training")
	@PostMapping
	public void create(@RequestBody TrainingDto dto) {
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
		trainingService.createTraining(dto);
	}

	@Operation(description = "Create a training")
	@PutMapping("{trainingId}")
	public void update(@PathVariable Long trainingId, @RequestBody TrainingDto dto) {
		dto.id = trainingId;
		PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		dto.description = sanitizer.sanitize(dto.description);
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

//	@Secured("ROLE_ADMIN") // #1 trebuie sa incepi cu ROLE_
//	@PreAuthorize("hasRole('ADMIN')") // #2 adauga el prefixul de ROLE_ cand cauta in lista de stringuri de authorities
//	@PostAuthorize() ???
	public void delete(@PathVariable Long trainingId) {
		trainingService.deleteById(trainingId);
//		if (user n-are voie) throw new AmFostDobitocException();
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
