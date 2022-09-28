package victor.training.spring.web.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

@Service

public class LanguageService {
    private final ProgrammingLanguageRepo repo;

    public LanguageService(ProgrammingLanguageRepo repo) {
        this.repo = repo;
    }

    @Cacheable("languages")
    @Logged
    public Flux<ProgrammingLanguage> findAll() {
//        new RuntimeException("not thrown, just for demo").printStackTrace(
//        );

        return repo.findAll();
    }
}
