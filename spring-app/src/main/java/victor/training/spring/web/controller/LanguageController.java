package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
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
//        System.out.println("Oare cu cine vorbesc? "
//                           + service.getClass());
        return service.findAll();
    }
    // curl
    @GetMapping("cache/kill")
    @CacheEvict("languages")
    public void killCache() {
        // metoda goala: nu ma sterge. lasa magia sa lucreze.........
    }
}


