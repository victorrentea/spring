package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService languageService;
    @GetMapping
    // TODO cache
    // TODO evict via dedicated endpoint (called from script)
    // at the end of the .sh script that inserts data, you need to tell the running apps to evict their cache.
    public List<LanguageDto> getAll() {
        return languageService.getEntities().stream().map(LanguageDto::new).collect(Collectors.toList());
    }

    private final CacheManager cacheManager; // programmatic alternative to @Cache*

    @GetMapping("evict")
//    @CacheEvict("languages")
    public void killLanguageCache() {
        cacheManager.getCache("languages").clear();
        // empty method. but please do not delete it. it's here only for the proxy in front to wipe the cache out
    }

}

@Service
@RequiredArgsConstructor
class LanguageService  {
    private final ProgrammingLanguageRepo repo;
    @Cacheable("languages")
    public List<ProgrammingLanguage> getEntities() {
        return repo.findAll();
    }

}



