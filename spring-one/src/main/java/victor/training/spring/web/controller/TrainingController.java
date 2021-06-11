package victor.training.spring.web.controller;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/trainings")
public class TrainingController {
   @Autowired
   private TrainingService trainingService;

   @GetMapping
   public List<TrainingDto> getAllTrainings() {
      return trainingService.getAllTrainings();
   }

   @GetMapping("{id}")
	@PreAuthorize("@securityService.hasAccessOnTraining(#id)")
	public TrainingDto getTrainingById(@PathVariable Long id) {
      TrainingDto dto = trainingService.getTrainingById(id);

      PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
      dto.description = sanitizer.sanitize(dto.description);

      return dto;
   }

   // TODO @Valid
   @PostMapping
   public void createTraining(@RequestBody TrainingDto dto) throws ParseException {
      trainingService.createTraining(dto);
   }

   @PreAuthorize("hasAuthority('training.edit') && @securityService.hasAccessOnTraining(#id)")
   @PutMapping("{id}")
	public void updateTraining(@PathVariable Long id, @RequestBody TrainingDto dto) throws ParseException {

//		HtmlPolicyBuilder html = new HtmlPolicyBuilder();
//		html.allowElements("b", "i","u");
//		dto.description = html.
      PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
      dto.description = sanitizer.sanitize(dto.description);

      trainingService.updateTraining(id, dto);
   }
   // TODO Allow only for role 'ADMIN'... or POWER or SUPER
   // TODO Allow for authority 'training.delete'
   // TODO Requirement: The current user manages the the teacher of that training (User.getManagedTeacherIds)
   // TODO @accessController.canDeleteTraining(#id)
   // TODO PermissionEvaluator

   @DeleteMapping("{id}")
//	@PreAuthorize("hasRole('ADMIN')")
   @PreAuthorize("hasAuthority('training.delete') && @securityService.hasAccessOnTraining(#id)")
//	@PreAuthorize("hasPermission(#id, 'TRAINING', 'DELETE')")
   public void deleteTrainingById(@PathVariable Long id) {
//      securityService.hasAccessOnTraining(id);

      trainingService.deleteById(id);
   }

   @Autowired
   SecurityService securityService;

   @PostMapping("search")
   public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
      return trainingService.search(criteria);
   }
}

@Component
class SecurityService {
   public boolean hasAccessOnTraining(Long id) {
      Training training = trainingRepo.findById(id).get();
      SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return securityUser.getManagedTeacherIds().contains(training.getTeacher().getId());
   }

   @Autowired
   private TrainingRepo trainingRepo;
}