package victor.training.spring.web.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LanguageService  {
    private final ProgrammingLanguageRepo repo;
    @Auditata("grav")
    @Cacheable("countries")
    @Timed("getLanguage")
    public List<LanguageDto> getLanguages() {
        log.debug("in fct");
//        new RuntimeException().printStackTrace();

        return repo.findAll().stream().map(LanguageDto::new).collect(Collectors.toList());
    }
}