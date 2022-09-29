package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.service.LanguageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping
    public Flux<LanguageDto> getAll() {
        System.out.println("Who am I talking to ?  " + languageService.getClass());
        Flux<ProgrammingLanguage> flux = languageService.findAll();
        System.out.println("What instance of flux is this ?? " +
                           System.identityHashCode(flux));
        return flux.map(LanguageDto::new);
    }
}