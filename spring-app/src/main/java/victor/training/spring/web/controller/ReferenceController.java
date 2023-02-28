package victor.training.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.entity.ProgrammingLanguage;

import java.util.List;

@RestController
public class ReferenceController {

  @GetMapping("api/languages")
  public List<ProgrammingLanguage> getAllLanguages() {
    return List.of(ProgrammingLanguage.values());
  }
}
