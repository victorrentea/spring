package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("rest/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable Long id) {
		return trainingService.getTrainingById(id);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}

	// after switching to DatabaseUserDetailsService
	// TODO [SEC] 1 Allow only for ROLE 'ADMIN'
	// TODO [SEC] 2 Authorize the user to have the authority 'deleteTraining'
	// TODO and @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator
   
	/** @see victor.training.spring.web.domain.UserProfile */
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	// De ce avem nevoie de un model de permisiuni, nu doar de roluri
//	@PreAuthorize("hasAnyRole('ADMIN', 'ADMIN_CL', 'ADMIN_REG')")
	// undeva, altcineva, va scrie si el ng-if='currentuser.role === 'ADMIN' || === 'ADMIN_CL' || ==='ADMIN_REG' (sau poate se prinde de array.find)
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}
