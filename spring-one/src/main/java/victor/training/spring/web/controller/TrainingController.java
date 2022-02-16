package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingDto.ValidationGroup.UpdateFlow;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/trainings")
//@PreAuthorize("hasRole('ADMIN')")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{trainingId}")
	public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long trainingId) {
		try {
			return ResponseEntity.ok(trainingService.getTrainingById(trainingId));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}


	///But as we see from the example - the resources are not there yet - the pouring is happening after the drinker gets the drinks . Is the ComputableFuture query returned with the computed drinks after all, or the data for the drinks themselves is returned in a secondary sent response?

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
//	@PreAuthorize("hasRole('ADMIN')") // the best
	@PreAuthorize("hasRole('training.edit')")
	public void updateTraining(@PathVariable Long id,@Validated(UpdateFlow.class) @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)

	// DaTA-security: you are not allowed to delete a training unless you are a manager for the trainer doing the training


	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

//	@RolesAllowed("ROLE_ADMIN")

//	@PreAuthorize("hasRole('ADMIN')") // the best
//	@PreAuthorize("hasAnyRole('ADMIN', 'POWER_USER', 'SUPERWOMAN')") // the weakness of the role-based authorization >
	@PreAuthorize("hasRole('training.delete') and @permissionManager.checkCanManageTraining(#id)") // the weakness of the role-based authorization >
//	@PreAuthorize("@permissionManager.canDeleteTraining(#id)") // the weakness of the role-based authorization >
	// DRY violation : repeat this list in the ui button
//	@PreAuthorize("hasPermission(#id, 'TRAINING', 'DELETE')") // delgate to a
	@DeleteMapping("{id}")

//	@PreAuthorize("hasRole('training.delete') and @permissionManager.checkCanManageTraining(#id)") // the weakness of the role-based authorization >
	// ACL - Access COntrol LIst

	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	@PostMapping("search") // for technical reasons
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}

