package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.TrainingService;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController // +add this to an @Import on a @Configuration
@RequestMapping("api/trainings")
@Slf4j
public class TrainingControllerStripped {
	@Autowired
	private TrainingService trainingService;
  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(MyException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR) // only target methods in this class
  public String onMyException(MyException exception, HttpServletRequest httpRequest) throws Exception {
    String errorMessageKey = "error." + exception.getCode().name();
    Locale clientLocale = httpRequest.getLocale(); // or from the Access Token
    String responseBody = messageSource.getMessage(errorMessageKey, exception.getParams(), exception.getCode().name(), clientLocale);
    log.error(exception.getMessage() + " : " + responseBody, exception);
    return responseBody + " SPECIFIC";
  }


  @GetMapping// GET /api/trainings
	public List<TrainingDto> getAllTrainings() {
		return trainingService.getAllTrainings();
	}

  @GetMapping("{trainingId}") // GET /api/trainings/17
  @Operation(description = "get by id, DUH!")
  public TrainingDto getTrainingById(
      @PathVariable Long trainingId/*,
      @PathVariable String ver*/) {
//    System.out.println("Ver: " + ver);
    log.trace("Print useful debug info (OCD-style)");
    return trainingService.getTrainingById(trainingId);
  }

  @PostMapping // POST /api/trainings {body} -> server generates the ID (DB sequence)
	public ResponseEntity<Void> createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
		var id = trainingService.createTraining(dto);


    URI urlOfNewlyCreatedResource = URI.create("/api/trainings/" + id);
    return ResponseEntity.created(urlOfNewlyCreatedResource)
        .header("your-stuff","is cool")
        .build();
	}
//  -> client generates the UUID
//  @PutMapping("{uuid}") // PUT /api/trainings/aada-afs-das-das-das-as-dc-123 {body with name, ...}
  // the server will attempt to insert this new ID in DB. fail if already there,
  // chances = 0, unless you retried. any retry will NOT create a new row, but fail with UK violation
  // ==> create becomes idempotent

  @PutMapping("{trainingId}")
	public void updateTraining(@PathVariable Long trainingId, @RequestBody @Valid TrainingDto dto) throws ParseException {
		dto.id = trainingId;
		trainingService.updateTraining(dto);
	}
	// TODO Allow only for role 'ADMIN'... or POWER or SUPER
	// TODO Allow for authority 'training.delete'
	// TODO The current user must manage the the teacher of that training
	//  	User.getManagedTeacherIds.contains(training.teacher.id)
	// TODO @accessController.canDeleteTraining(#id)
	// TODO PermissionEvaluator

  @DeleteMapping("{id}")
	public void deleteTrainingById(@PathVariable Long id) {
		trainingService.deleteById(id);
	}

//  @GetMapping
//	public List<TrainingDto> search(
//      @RequestParam(required = false) String name,
//      @RequestParam(required = false) Long teacherId) {
//    TrainingSearchCriteria criteria = new TrainingSearchCriteria();
//    criteria.name = name;
//    criteria.teacherId = teacherId;

  // for large criteria, the long URL can be truncated > 2000 (lost) by servers on the way
  // for sensitive criteria (phonenumbers, address) -> the URL gets saved by some servers
  // ==> criteria -> BODY

//  @GetMapping
//	public List<TrainingDto> search(TrainingSearchCriteria criteria) {// spring automatically maps requests query params ?name= to fields of this obj

//  @GetMapping
//	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
  // it works on my machine

  @PostMapping("search")
	public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
		return trainingService.search(criteria);
	}
}
