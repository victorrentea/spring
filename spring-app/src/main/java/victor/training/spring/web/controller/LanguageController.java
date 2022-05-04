package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final ProgrammingLanguageRepo repo;

    @GetMapping
    @Cacheable("languages")
    public List<LanguageDto> getAll() {
        return repo.findAll().stream().map(LanguageDto::new).collect(Collectors.toList());
    }
}
