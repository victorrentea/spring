package victor.training.spring.web.controller;

import io.micrometer.core.annotation.Timed;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;


public class TrainingController {
	private TrainingService trainingService;
  // Tip#1: run StartDatabase.java then SpringApplication.java
  //   then navigate to http://localhost:8080/ for a trivial UI
  // Tip#2: Inspire from TeachersController

  // TODO eg GET /api/trainings
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

  // TODO eg GET /api/trainings/3
	public TrainingDto getTrainingById(Long id) {
		return trainingService.getTrainingById(id);
	}

  // TODO eg POST /api/trainings
	// TODO validation: training MUST have a name of at least 3 characters
  // TODO validation: training MUST have a startDate set
	public void createTraining(TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

  // TODO eg PUT /api/trainings/3
  // TODO same validation rules above should apply
	public void updateTraining(Long trainingId, TrainingDto dto) throws ParseException {
		dto.id = trainingId;
		trainingService.updateTraining(dto);
	}
  // TODO eg DELETE /api/trainings/3
	// TODO Allow only for role 'ADMIN'
	public void deleteTrainingById(Long id) {
		trainingService.deleteById(id);
	}

  // TODO eg POST /api/trainings/search
	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
