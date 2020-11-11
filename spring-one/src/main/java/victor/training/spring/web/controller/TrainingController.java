package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.service.TrainingService;

@RestController
@RequestMapping("rest/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;
	@Autowired
	private TrainingRepo trainingRepo;

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
	// TODO ai voie sa stergi un training doar daca ai 'deleteTraining'
	    //  dar in plus teacherul acelui curs este in lista ta de teacheri manageuiti (de userul tau)
	// TODO PermissionEvaluator

//	@Value("#{permissionManager.canDeleteTraining(1L)}")
//	private String ceva;

	/** @see victor.training.spring.web.domain.UserProfile */
	@DeleteMapping("{id}")
//	@PreAuthorize("hasRole('ADMIN')") // role-based security
//	@PreAuthorize("@permissionManager.canDeleteTraining(#id)") // role-based security
	@PreAuthorize("hasPermission(#id, 'TRAINING', 'DELETE')")
	public void deleteTrainingById(@PathVariable Long id) {

		trainingService.deleteById(id);
	}

	@Autowired
	PermissionManager permissionManager;


	// De ce avem nevoie de un model de permisiuni, nu doar de roluri
//	@PreAuthorize("hasAnyRole('ADMIN', 'ADMIN_CL', 'ADMIN_REG')")
	// undeva, altcineva, va scrie si el ng-if='currentuser.role === 'ADMIN' || === 'ADMIN_CL' || ==='ADMIN_REG' (sau poate se prinde de array.find)
	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}
