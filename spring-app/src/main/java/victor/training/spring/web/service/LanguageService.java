package victor.training.spring.web.service;

import org.springframework.stereotype.Service;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;

@Service
public class LanguageService {
    private final ProgrammingLanguageRepo repo;

    public LanguageService(ProgrammingLanguageRepo repo) {
        this.repo = repo;
    }

    public List<ProgrammingLanguage> findAll() {
        return repo.findAll();
    }
}
