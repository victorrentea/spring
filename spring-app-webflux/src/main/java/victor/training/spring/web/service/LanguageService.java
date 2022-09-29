package victor.training.spring.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

@Service

public class LanguageService {
    private static final Logger log = LoggerFactory.getLogger(LanguageService.class);
    private final ProgrammingLanguageRepo repo;

    public LanguageService(ProgrammingLanguageRepo repo) {
        this.repo = repo;
    }

    @Cacheable("languages")
    @Logged
    public Flux<ProgrammingLanguage> findAll() {
        log.debug("Get languages"); // how many times does the query run ? check logs
        return repo.findAll();
    }
}
