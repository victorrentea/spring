package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LanguageService {
    private final ProgrammingLanguageRepo repo;

    // TODO cache
    // TODO evict via dedicated endpoint (called from script)
    public List<LanguageDto> findAll() {
        return repo.findAll().stream().map(LanguageDto::new).collect(Collectors.toList());
    }
}
