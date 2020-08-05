package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/rest/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	// TODO [SEC] Restrict display for trainings of teachers of users
//	@RequestMapping(path = "/rest/trainings", method = GET)
	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	// TODO [SEC] Check user manages training of this training
	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO [SEC] Check user manages teacher of this training
	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}



	@PreAuthorize("hasRole('ADMIN')")
	// TODO [SEC] 1 Allow only for ROLE 'ADMIN'
	// TODO [SEC] 2 Authorize the user to have the authority 'deleteTraining'
	// TODO and @accessController.canDeleteTraining(#id)
	/** @see victor.training.spring.web.domain.UserProfile */
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}
