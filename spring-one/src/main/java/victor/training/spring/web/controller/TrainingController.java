package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	public TrainingDto getTrainingById(Long id) {
		return trainingService.getTrainingById(id);
	}
	public void createTraining( TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	public void updateTraining(Long id, TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.edit'
	// TODO Requirement: The current user manages the the teacher of that training (User.getManagedTeacherIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

	public void deleteTrainingById(Long id) {
		trainingService.deleteById(id);
	}

	// TODO
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
