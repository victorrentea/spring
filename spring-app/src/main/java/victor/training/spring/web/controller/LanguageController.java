package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final ProgrammingLanguageRepo repo;
    private final LanguageService languageService;

    @GetMapping
    // TODO cache
    // TODO evict via dedicated endpoint (called from script)
    public List<LanguageDto> getAll() {
        log.debug("Chem fct pe " + languageService.getClass());
        return getLanguages();
    }

    @Cacheable("countries")
    public List<LanguageDto> getLanguages() {
        log.debug("in fct");
        return repo.findAll().stream().map(LanguageDto::new).collect(Collectors.toList());
    }

}


//class Tzeapa extends LanguageService {
//    @Override
//    public List<LanguageDto> getLanguages() {
//daca am in cache da din cacjhe altfel cheam-o si apoi pune0n cache
//        return super.getLanguages();
//    }
//}