package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;
import victor.training.spring.web.MyException.ErrorCode;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.TrainingService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/trainings")  // @Resource("api/trainings")
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping // @GET @Resource("api/trainings")
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}
	@GetMapping ("by-name") // api/trainings/by-name?name=ceva
	public List<TrainingDto> getTrainingsByName( @RequestParam String name) {
		System.out.println("Query by name " + name);
		return Collections.emptyList();
	}

	@GetMapping("{id}")
	public TrainingDto getTrainingById(@PathVariable long id) {
		return trainingService.getTrainingById(id);
	}

	@PostMapping
	public void createTraining(@Valid @RequestBody TrainingDto dto) throws ParseException {
//		try {
			trainingService.createTraining(dto);
//			return ResponseEntity.ok("");
//		} catch (IllegalArgumentException e) {
//			 fac cumva intorc un raspuns care sa zica eroare.
//			return new ResponseEntity<String>("Naspa, exista deja cursul", HttpStatus.BAD_REQUEST);
//		}
	}

	@PutMapping("{id}/start-date")
	public void updateTrainingStartDate(@PathVariable Long id, @RequestBody Date date) {
		trainingService.updateTrainingStartDate(id, date);
	}
	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {
		trainingService.updateTraining(id, dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO Requirement: The current user manages the the teacher of that training (User.getManagedTeacherIds)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

	@DeleteMapping("{id}")
//	@RolesAllowed("ROLE_ADMIN")
	@PreAuthorize("hasAnyRole('ADMIN')")
//	@PreAuthorize("hasAuthority('DELETE_TRAINING')")
	public void deleteTrainingById(@PathVariable Long id) {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<Long> managedTeacherIds = securityUser.getManagedTeacherIds();
//		User user = userRepo.getForLogin(currentUsername).get(); // ineficient sa faci un SELECT in DB pentru userul curent.
		// intr-o app web, userul curent trebuie pastrat in sesiunea curenta pus acolo de la login.
//		Set<Long> managedTeacherIds = user.getManagedTeacherIds();

		Training training = trainingRepo.findById(id).get();

		Long teacherId = training.getTeacher().getId();

		if (!managedTeacherIds.contains(teacherId)) {
			throw new MyException(ErrorCode.NOT_ALLOWED_TO_DELETE);
		}

//		SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
		trainingService.deleteById(id);
	}

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TrainingRepo trainingRepo;

	// TODO
	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
	@PostMapping("list")
	public List<String> search(@RequestBody List<String> strings) {
		return strings.stream().map(String::toUpperCase).collect(Collectors.toList());
	}
}
