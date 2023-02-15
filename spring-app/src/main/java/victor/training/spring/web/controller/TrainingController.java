package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.service.TrainingService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	private final TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable TrainingId id) {
		return trainingService.getTrainingById(id.id());
		//TODO if id is not found, return 404 status code
		// you should never be afraid of letting go a runtime exception to flow out in a REST API
	}
//
//	@GetMapping("teachers/kill-cache")
////	@CacheEvict("teacher-bio") // asumes the method takes as a param the key to evict to evict from cache
//	@CacheEvict(value = "teacher-bio", allEntries = true)
//	public void empty() {
//
//	}

	@PostMapping
	public void createTraining(@RequestBody @Validated TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
//	public void updateTraining(@PathVariable Long id, @RequestBody UpdateTrainingRequest dto) throws ParseException {
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'
	// TODO Fix UX
	// TODO Allow also 'POWER'; then remove it. => OWASP top 10
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


	// TODO for searches, should we use GET or POST ?
	// in theory search is a GETs data. ?p1=asdasafs&p2=afgajgajgsadjkghksjghf&

	@PostMapping("search")
	public List<TrainingDto> searchUsingPOST(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	@GetMapping("search") // OMG does the same as the above one
	public List<TrainingDto> searchUsingGET(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}


//	@GetMapping("search")
//	public List<TrainingDto> searchUsingGET(
//					@RequestParam(required = false) String name,
//					@RequestParam(required = false) Long teacherId) {
//		return trainingService.search(new TrainingSearchCriteria().setName(name).setTeacherId(teacherId));
//	}

}
