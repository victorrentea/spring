package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

public interface TrainingControllerStrippedApi {
    @GetMapping
    List<TrainingDto> getAllTrainings();

    @GetMapping("{id}")
    TrainingDto getTrainingById(@PathVariable Long id);

    //@GetMapping
    //	public void method(HttpServletResponse response) {
    //		response.getWriter() // jackson sa scrii datele ca JSON pe writer
    //		response.setTrailerFields();
    //
    //	}
    @PostMapping
    void createTraining(@RequestBody @Valid TrainingDto dto) throws ParseException;

    @Operation(description = "Actualizeaza Training")
    // @Aspect ....
    @PutMapping("{id}")
    void updateTraining(@PathVariable Long id, @RequestBody @Valid TrainingDto dto) throws ParseException;

    @DeleteMapping("{id}/delete")
    void deleteTrainingById(@PathVariable Long id);


    //@PreAuthorized("ha")
    //	@Secured("ADMIN")
    //	@GetMapping // nu GetMapping pentru ca asta cere toti param sa vina in url ?name=asda%40sdaf&teacherId=1&
    @PostMapping("search")
    // de ce nu POST? => nu creezi nimic, ci doar obtii data, nu e HTTP/REST pur
    List<TrainingDto> search(@RequestBody TrainingSearchCriteria criteria);
}
