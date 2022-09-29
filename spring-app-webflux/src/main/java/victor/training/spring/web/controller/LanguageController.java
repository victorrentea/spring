package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.service.LanguageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping
    public Flux<LanguageDto> getAll() {
        System.out.println("Who am I talking to ?  " + languageService.getClass());
        return languageService.findAll()
                .map(LanguageDto::new);
    }
}