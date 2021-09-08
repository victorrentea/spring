package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import javax.servlet.http.HttpServletRequest;
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
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{id}")
//	@RolesAllowed("ROLE_ADMIN")
	@PreAuthorize("hasAnyRole('ADMIN')")
//	@AdminOnlyr
	public void deleteTrainingById(@PathVariable Long id) {
//		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("sesiune.dat"))) {
//			objectOutputStream.write(new Mare());
//		}

		trainingService.deleteById(id);
	}

	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}


class Mare implements Serializable {
	private String string;
}