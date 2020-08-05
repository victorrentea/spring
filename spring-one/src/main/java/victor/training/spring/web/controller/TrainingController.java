package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.EnumMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

import static java.util.Arrays.asList;

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



//	@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAuthority('deleteTraining')")

	// TODO [SEC] 1 Allow only for ROLE 'ADMIN'
	// TODO [SEC] 2 Authorize the user to have the authority 'deleteTraining'
	// TODO and @accessController.canDeleteTraining(#id)
	/** @see victor.training.spring.web.domain.UserProfile */
	@DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

	@PostMapping
	public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}
}

//
//enum TriggerType {
//	TIMESTAMP(new TimestampTypeStrategy()),//(true, false, 10, SECONDS),
//	LOCATION,
//	LANGUAGE
//
//	;
//
//	public final TriggerTypeStrategy strategy;
//
//	TriggerType(TriggerTypeStrategy strategyClass) {
//
//		this.strategy = strategyClass;
//	}
//
//	public List<String> getEntityList() {
//		return TriggerEntityList.TRIGGER_TYPE_LIST.get(this);
//	}
//}
//
//
//interface TriggerTypeStrategy {
//	List<String> getEntityList();
//	List<String> getEntityList2();
//	String getEntityList3();
//
//}
//class TimestampTypeStrategy implements TriggerTypeStrategy{
//
//}
//
//class TriggerEntityList {
//	public static final EnumMap<TriggerType, List<String>> TRIGGER_TYPE_LIST = new EnumMap<>(TriggerType.class);
//	static {
//		TRIGGER_TYPE_LIST.put(TriggerType.TIMESTAMP,  asList("a","b"));
//		for (TriggerType value : TriggerType.values()) {
//			if (!TRIGGER_TYPE_LIST.containsKey(value)) {
//				throw new IllegalArgumentException();
//			}
//		}
//	}
//
//}