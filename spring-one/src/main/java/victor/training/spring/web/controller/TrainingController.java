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

	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'deleteTraining'
	// TODO Requirement: A training can only be deleted by user managing the teacher of that training (User.getManagedTeachedIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator
	public void deleteTrainingById(Long id) {
		trainingService.deleteById(id);
	}

	public void createTraining(TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}
