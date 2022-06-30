package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TrainingRepo;

import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DemoDBConnStarvation {
    private final TrainingRepo trainingRepo;

//    @Transactional
    @GetMapping("api/load/heavy")
    public int method() {
        // aduc niste dae.
        List<Training> all = trainingRepo.findAll(); // nu mergea inainte: tinea conn
        restLoadDeLaAltAPI(Collections.emptyList());
        return all.size();
    }
    @GetMapping("api/load/banal")
    public String method(Long id) { // termine super repede
      return trainingRepo.findById(id).orElseThrow().getName();
    }

    private void restLoadDeLaAltAPI(List<Training> all) {
        // RestTemaplate
        ThreadUtils.sleepq(1000);
    }
}
