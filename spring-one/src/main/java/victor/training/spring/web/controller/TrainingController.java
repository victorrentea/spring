package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	// TODO [SEC] Restrict display for trainings of teachers of users
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	// TODO [SEC] Check user manages training of this training
	public TrainingDto getTrainingById(Long id) {
		return trainingService.getTrainingById(id);
	}

	// TODO [SEC] Check user manages teacher of this training
	public void updateTraining(Long id, TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// after switching to DatabaseUserDetailsService
	// TODO [SEC] 1 Allow only for ROLE 'USER'
	// TODO [SEC] 2 Authorize the user to have the authority 'deleteTraining'
	// TODO and @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator
   
	/** @see victor.training.spring.web.domain.UserProfile */
	public void deleteTrainingById(Long id) {
		trainingService.deleteById(id);
	}

	public void createTraining(TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}
