package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/trainings")
public class TrainingControllerStripped {
  @Autowired
  private TrainingService trainingService;

  @GetMapping
  public List<TrainingDto> getAllTrainings() {
    return trainingService.getAllTrainings();
  }

  @GetMapping("{trainingId}")
  public TrainingDto getTrainingById(@PathVariable Long trainingId) {
    return trainingService.getTrainingById(trainingId);
  }
  // asa NU:
//  public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long trainingId) {
//    try {
//      return ResponseEntity.ok(trainingService.getTrainingById(trainingId));
//    } catch (NoSuchElementException e) { // niciodata asa!
//      return ResponseEntity.notFound().build();
//    }
//  }

  @PostMapping
  public void createTraining(
      @RequestBody @Validated TrainingDto dto) throws ParseException {
    trainingService.createTraining(dto);
  }

  @PutMapping("{trainingId}")
  public void updateTraining(
      @PathVariable Long trainingId,
      @RequestBody @Validated TrainingDto dto) throws ParseException {
    dto.id = trainingId;
    trainingService.updateTraining(dto);
  }



  @PostMapping("search") // #traditie
  public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
    return trainingService.search(criteria);
  }

  @GetMapping("search") // GET /api/trainings/search?name=Java&date=2021-12-01
  public List<TrainingDto> searchGet(TrainingSearchCriteria criteria) {
    System.out.println("params cititi din ? = " + criteria);
    return trainingService.search(criteria);
  }

  @DeleteMapping("{id}")
//  @Secured("ROLE_ADMIN") // !!! mereu stringu incepe cu ROLE_ !!!
  @PreAuthorize("hasRole('ADMIN') && @authorizationService.areVoie(#id)") // SpEL
  public void deleteTrainingById(@PathVariable Long id) {
    trainingService.deleteById(id);
  }
}
@Component
class AuthorizationService {
  public boolean areVoie(long trainingId) {
    System.out.println("#sieu");
    return true;
  }
}