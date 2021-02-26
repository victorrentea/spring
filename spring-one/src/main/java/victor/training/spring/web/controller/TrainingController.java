package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("rest/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

//	@GetMapping("{id}")
//	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
//		TrainingDto dto = trainingService.getTrainingById(id);
//		if (dto == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(dto);
//	}
	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.edit'
	// TODO Requirement: The current user manages the the teacher of that training (User.getManagedTeacherIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	@Autowired
	CurrentSession currentSession;

	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto, HttpSession session) throws ParseException {
		trainingService.createTraining(dto);
		currentSession.setLastCreate(dto);
//		session.setAttribute("last-create", dto);
	}

	@GetMapping("last")
	public TrainingDto getLastCreateData(HttpSession session) {
		return ciudat.biz(session);
	}
	@Autowired
	CiudatFrate ciudat;

}

@RequiredArgsConstructor
@Component
class CiudatFrate {
	private final CurrentSession currentSession;

	public TrainingDto biz(HttpSession session) {
		System.out.println("De fapt curretSession nu e chiar o instanta reala ci e un " + currentSession.getClass());
//		return (TrainingDto) session.getAttribute("last-create");
		return currentSession.getLastCreate();
	}
}


@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@Component
class CurrentSession {
	private TrainingDto lastCreate;
}

//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Data
//@Component
//class CurrentRequest {
//	private long userId;
//	private Locale browserLocale;
//	private long tenantId;
//
//}
