package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.User;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController implements TrainingControllerStrippedApi {
	@Autowired
	private TrainingService trainingService;

	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	public TrainingDto getTrainingById(Long id) {
		return trainingService.getTrainingById(id);
	}

	public void createTraining(TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	public void updateTraining(Long id, TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}
	// TODO Better UX please !
	// TODO Allow only for role 'ADMIN'... or 'POWER'

	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

//	@Secured("ROLE_ADMIN") // utila cand faci role-based authorizaton
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@PreAuthorize("hasAuthority('training.delete') and @permissionManager.canDeleteTraining(#id)") // more fine-grained authz
	public void deleteTrainingById(Long id) {
//		canDeleteTraining(id);

		trainingService.deleteById(id);
	}


	public List<TrainingDto> search(TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
