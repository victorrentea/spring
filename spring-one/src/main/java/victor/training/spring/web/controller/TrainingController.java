package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

@RestController
@RequestMapping("rest/trainings")
public class TrainingController {
	private static final Logger log = LoggerFactory.getLogger(TrainingController.class);
	@Autowired
	private TrainingService trainingService;

	// TODO [SEC] Restrict display for trainings of teachers of users
	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	// TODO [SEC] Check user manages training of this training
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	@PutMapping("{id}")
	// TODO [SEC] Check user manages teacher of this training
	public void updateTraining(@PathVariable  Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}


	// after switching to DatabaseUserDetailsService
	// TODO and @accessController.canDeleteTraining(#id)
	/** @see victor.training.spring.web.domain.UserProfile */
	@PreAuthorize("@trainingAuthorizer.hasControlOnTraining(#id)") // in .jsx ai pune un if(deleteTraining) { <button delete> }
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		// poti sterge un training doar daca userul curent are in lista lui de teacherIds training.teacher.id
		// aka Jurisdictions / Access-Control-List / permisiuni pe date.

//		if (!trainingAuthorizer.hasControlOnTraining(id)) {
//			throw new IllegalArgumentException("Nu");
//		}
		trainingService.deleteById(id);
	}

	@Autowired
	private TrainingAuthorizer trainingAuthorizer;


	@PostMapping
	public void createTraining(@RequestBody  TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}
