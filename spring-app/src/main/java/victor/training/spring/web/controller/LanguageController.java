package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.service.LanguageService;
import victor.training.spring.web.service.ServiceInterface;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService languageService;
    private final ServiceInterface service;

    @GetMapping
    // TODO cache
    // TODO evict via dedicated endpoint (called from script)
    public List<LanguageDto> getAll() {
        System.out.println("Who am I talking to ?  " + languageService.getClass());
        return languageService.findAll()
                .stream().map(LanguageDto::new).collect(Collectors.toList());
    }

    @GetMapping("test")
    public int method() {
        return service.stuff(1);
    }
}
// Spring will use a lib called CG lib to generate a subclass at runtime of your proxied classes,
// then injecting this proxy instead of a ref to your class to anyone needing it
//class