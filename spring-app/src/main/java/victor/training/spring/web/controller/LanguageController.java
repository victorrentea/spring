package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService service;

    @GetMapping
    // TODO cache
    // TODO evict via dedicated endpoint (called from script)
    public List<LanguageDto> getAll() {
        System.out.println("Vorbesc cu un proxy " + service.getClass());
        return service.getAll();
    }
}

@Service
@RequiredArgsConstructor
class LanguageService {
    private final ProgrammingLanguageRepo repo;

    @Cacheable("languagesX")
    public List<LanguageDto> getAll() {
        System.out.println("Acum execut functia");
        return repo.findAll()
                .stream()
                .map(LanguageDto::new)
                .collect(toList());
    }
}
// un interceptor(proxy) care primeste toate apelurile venite catre metode din clasa LanguageService ????
    // la runtime, Spring genereaza o subclasa a lui Language Service
    // si iti injecteaza la linia 23, subclasa lui.
// interceptorul se uita ce metoda chemi, si daca vede ca invoci o metoda adnotata cu @Cacheable atunci
// daca deja ai datele in memorie, le intoarce direct, altfel lasa apelul real sa execute si retine ulterior datele intoarse.

