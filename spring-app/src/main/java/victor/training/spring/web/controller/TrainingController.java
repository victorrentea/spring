package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
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

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.ResponseEntity.ok;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/trainings") // prefixul comun dupa [localhost:8080]
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@GetMapping // GET /api/trainings
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

	@GetMapping("{id}")  // GET /api/trainings/*
	public TrainingDto getTrainingById(@PathVariable /*TrainingId*/ long id) {
//		try {
//			return ok(trainingService.getTrainingById(id));
//		} catch (java.util.NoSuchElementException e) {
//			return notFound().build();
//			// overengineering daca singurul client al acestui
//			// API este propriul tau FE
//			// trup din trupul tau. Echiupa ta. Tu ca esti Full stack! 💪
//
//			// ARE SENS doar daca API-ul tau este contractual semnat agreat,. ai un WORD file/confluence in care publici api.
//			// Exporti OpenAPI  3 din endpoint >> tre sa fii mai formal
//		}
		return trainingService.getTrainingById(id);
		//TODO if id is not found, return 404 status code
	}

	// GET
	// POST creezi
	// PUT overwrite
	// DELETE
	// PATCH update partial
	// TRACE never
	// OPTIONS pt CORS

	// TODO @Valid
	@PostMapping
	public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
		trainingService.createTraining(dto);
	}

	@PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @Valid @RequestBody TrainingDto dto) throws ParseException {
		// TODO what if id != dto.id
		trainingService.updateTraining(id, dto);
	}

	// TODO Allow only for role 'ADMIN' or 'POWER_USER'
	// TODO Allow for authority 'training.delete'
	// TODO Allow any action only if the current user manages the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator [GEEK]
	@DeleteMapping("{trainingId}/delete")
//	@Secured("ADMIN")
//	@PreAuthorize("hasAnyRole('ADMIN','POWER_USER')")
	@PreAuthorize("hasAuthority('training.delete') && @permissionChecker.hasAccessOnTraining(#trainingId)")

	public void deleteTrainingById(@PathVariable Long trainingId) {

		trainingService.deleteById(trainingId);
	}


	// TODO GET or POST ?
	@PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}


@RequiredArgsConstructor
@Component
class PermissionChecker {
	private final UserRepo userRepo;
	private final TrainingRepo trainingRepo;
	public boolean hasAccessOnTraining(Long trainingId) {
		KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String language = (String) keycloakPrincipal.getKeycloakSecurityContext().getIdToken().getOtherClaims()
				.get("language");

		System.out.println("LAnguage = " +  language);
		User userFromMyDB = userRepo.getForLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

		Training training = trainingRepo.findById(trainingId).orElseThrow();
		return userFromMyDB.getManagedTeacherIds().contains(training.getTeacher().getId());
	}
}


//@Component
class BeanPostProcessorCareVerficaAdnotarile implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//		System.out.println("Scanning  bean  " + bean.getClass());
		for (Method method : bean.getClass().getMethods()) {
			PreAuthorize a = method.getAnnotation(PreAuthorize.class);
			if (a != null) {
				Pattern p = Pattern.compile("hasRole\\('(\\w+)'\\)");
				Matcher m = p.matcher(a.value());
				if (!m.matches()) {
					throw new IllegalArgumentException("no match");
				}
				if (!Set.of("USER", "ADMIN").contains(m.group(1))) {
					throw new IllegalArgumentException("Invalid annotation value: " + a.value());
				}
			}
		}
		return bean;
	}
}



