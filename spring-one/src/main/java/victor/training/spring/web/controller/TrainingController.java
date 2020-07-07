package victor.training.spring.web.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.TrainingService;

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

	// after switching to DatabaseUserDetailsService
	// TODO [SEC] 1 Allow only for ROLE 'ADMIN'
	// TODO [SEC] 2 Authorize the user to have the authority 'deleteTraining'
	// TODO and @accessController.canDeleteTraining(#id)
	/** @see victor.training.spring.web.domain.UserProfile */
	@PreAuthorize("hasAuthority('deleteTraining')")
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		Training training = trainingRepo.findById(id).get();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser principal = (SecurityUser) authentication.getPrincipal();

		if (!principal.getManagedTeacherIds().contains(training.getTeacher().getId())) {
			throw  new RuntimeException("ia mana!");
		}
		trainingService.deleteById(id);
	}

	@Autowired
	private TrainingRepo trainingRepo;



	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
	@GetMapping("tomorrow")
	public LocalDate getTomorrow(@RequestParam String dateStr) {
		LocalDate date = LocalDate.parse(dateStr);
//	public LocalDate getTomorrow(@RequestParam LocalDate date) {
//		LocalDate date = LocalDate.parse(dateStr);
		return date.plusDays(1);
	}
}
