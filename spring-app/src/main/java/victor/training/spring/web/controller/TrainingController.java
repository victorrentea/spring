package victor.training.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.props.Props;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.service.NotificationService;
import victor.training.spring.web.service.TrainingService;

import java.text.ParseException;
import java.util.List;

// Spring o detecteaza intrucat e un @Component
// fratii ei: @Service // business logic
// fratii ei: @Repository // lucru cu DB:SQL/Elasic/inmem
@RestController // voi trata req http ->@Controller->@Component
@RequestMapping("/api/trainings") // prefix comun
@RequiredArgsConstructor // genereaza ctor pt toate campurile finale
@Slf4j // log
public class TrainingController {
  private final TrainingService trainingService;
//  @Value("${props.env}")
//  private final String propsEnv;
  private final Props props;
  private final NotificationService notificationService;
  // lombokul nu va copia adnotarea @Value decat daca-l rogi cu lombok.config

  @GetMapping("config")
  public String getConfig() {
    notificationService.notify("Barfa!");
    return props.env();
  }

  // TODO injectati 'props.env' din config intr-un camp si expuneti valoarea ei
  //  intr-un endpoint /api/trainings/config
  // Hint: ChatGPT va va sugera sa adnotati campul cu @Value("${...}")

  @GetMapping
  public List<TrainingDto> getAllTrainings() {
    return trainingService.getAllTrainings();
    // tu nu chemi de fapt metoda pe care crezi tu.
    // ci spring iti "FURA" apelul pt ca ti-a injectat un PROXY la clasa ceruta.
    // ca sa te pacaleasca, si sat- intercepteze apelul sa-ti ia metrica.
  }

  @GetMapping("{id}")
  public TrainingDto getTrainingById(@PathVariable Long id) {
    return trainingService.getTrainingById(id);
  }

  // TODO validation: training MUST have a startDate set
  @PostMapping
  public void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException {
    trainingService.createTraining(dto);
  }

  // TODO eg PUT /api/trainings/3
  // TODO same validation rules above should apply
  @PutMapping("{trainingId}")
  public void updateTraining(
      @PathVariable Long trainingId,
      @RequestBody @Validated TrainingDto dto) throws ParseException {
    dto.id = trainingId;
    trainingService.updateTraining(dto);
  }

  // TODO maine, de altu- Allow only for role 'ADMIN'
  @DeleteMapping("{id}")
  public void deleteTrainingById(@PathVariable Long id) {
    trainingService.deleteById(id);
  }

  // GET api/trainings?name=&pret=ieftin&cnpulmu=&shippingaddres=bunkeru_meu|||||||| 2000
  // GET api/trainings SOC are body din 202, dar NU-l folosi
  // TODO eg POST /api/trainings/search
  @PostMapping("search")
  public List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria) {
    return trainingService.search(criteria);
  }
}
