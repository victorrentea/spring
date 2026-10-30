package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.security.AccessController;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.service.TrainingService;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/trainings")
public class TrainingController implements TrainingApiDocs {
  private final TrainingService trainingService;
  private final AccessController accessController;

  @GetMapping
  public List<TrainingDto> getAll() {
    return withCanDelete(trainingService.getAllTrainings());
  }

  @GetMapping("{id}")
  public TrainingDto get(@PathVariable /*TrainingId*/ long id) {
    TrainingDto dto = trainingService.getTrainingById(id);
    accessController.fillCanDelete(List.of(dto));
    return dto;
    //TODO return 404 if not found
  }

  @Override
  @PostMapping
  public void create(@RequestBody @Validated TrainingDto dto) {
    trainingService.createTraining(dto);
  }

  @PutMapping("{trainingId}")
  public void update(@PathVariable Long trainingId, @RequestBody TrainingDto dto) {
    dto.id = trainingId;
    trainingService.updateTraining(dto);
  }

  // TODO Fix UX
  // TODO Allow also for 'POWER' role; then remove it. => update UI but forget the BE
  // TODO Allow only if the current user manages the programming language of the training
  //  (comes as 'admin_for_language' claim in in KeyCloak AccessToken)
  //  -> use SpEL: @accessController.canDeleteTraining(#id)
  //  -> hasPermission + PermissionEvaluator [GEEK]
  @PreAuthorize("@accessController.canDeleteTraining(#trainingId)")
  @DeleteMapping("{trainingId}")
  public void delete(@PathVariable Long trainingId) {
    trainingService.deleteById(trainingId);
  }

  private List<TrainingDto> withCanDelete(List<TrainingDto> trainings) {
    accessController.fillCanDelete(trainings);
    return trainings;
  }

  private final TrainingRepo trainingRepo;


  // === Search ===
  // TODO 'search' should use GET or POST ?
  // GET:
  // POST:

  // == #1 POST ==
  @PostMapping("search") // traditional
  // 😊 hides sensitive search criteria from URL eg ?phone=0158135767
  public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
    return withCanDelete(trainingService.search(criteria));
  }

  // == #2 GET + query params: search?name=xxx&teacherId=yyy ==
  // 😊 users can send search URLs to friends
  // 🙁 but url <= 2000 characters
  @GetMapping("search") // traditional
  public List<TrainingDto> searchUsingGET(
          @RequestParam(required = false) String name,
          @RequestParam(required = false) Long teacherId) {
    return withCanDelete(trainingService.search(new TrainingSearchCriteria().name(name).teacherId(teacherId)));
  }

  // GET + query params, but captured as a DTO
  @GetMapping("search2")
  public List<TrainingDto> searchUsingGET(TrainingSearchCriteria criteria) {
    return withCanDelete(trainingService.search(criteria));
  }

  // ❌ GET + Body avoid - not fully supported
  @GetMapping("searchGetBody")
  public List<TrainingDto> searchUsingGETBody(@RequestBody TrainingSearchCriteria criteria) {
    return withCanDelete(trainingService.search(criteria));
  }

  @Value("classpath:/static/spa.jpg")
  private Resource picture;

  @GetMapping("download")
  public ResponseEntity<Resource> downloadFile() throws IOException {
    return ResponseEntity.ok()
            .contentType(IMAGE_JPEG)
            .contentLength(picture.contentLength())
//						.header("Content-Disposition", "attachment; filename=\"spa.jpg\"") // tells browser to download as file
            .body(picture);
  }

}
