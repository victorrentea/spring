package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingSecurity;
import victor.training.spring.web.service.TrainingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings(HttpServletRequest request) {
		request.getSession()
			.setAttribute("nisteData","10MB"); // x 1000

		// Serialiable
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
//		@PreAuthorize("@trainingSecurity.canUpdateTraining(#id)")
	public TrainingDto getTrainingById(@PathVariable Long id,HttpServletRequest request) {
		System.out.println(request.getSession().getAttribute("nisteData"));
		return trainingService.getTrainingById(id);
	}

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	//	@PreAuthorize("hasAnyRole('ADMIN','POWER_USER') and @trainingSecurity.canUpdateTraining(#id)")
	//@CanUpdateTraining
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto, HttpSession httpSession) throws ParseException {
//		trainingSecurity.checkCanUpdateTraining(id);

//		RestTemplate rest = new RestTemplate();
//		ResponseEntity<String> response = rest.exchange(RequestEntity.post("")
//			.header("Cookie", "JSESSIONID:",
//					(String) httpSession.getAttribute("COOKIE_PT_SIS_INTERN")).body("a"), String.class);
//
//		response.getHeaders().get("Cookie");


		trainingService.updateTraining(id, dto);
	}

	@Autowired
	TrainingSecurity trainingSecurity;

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
//	@RolesAllowed("ROLE_ADMIN")
//	@CanUpdateTraining
//	@PreAuthorize("hasAnyRole('ADMIN', 'POWER')")// and @trainingSecurity.canUpdateTraining(#id)")
//	@PreAuthorize("hasAuthority('training.delete')")
	public void deleteTrainingById(@PathVariable Long id) {


		trainingService.deleteById(id);
	}

	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}


class Mare implements Serializable {
	private String string;
}