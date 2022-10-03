package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class LanguageService {
    private final ProgrammingLanguageRepo repo;

    @Cacheable("languages")
    public List<LanguageDto> findAll() {
        System.out.println("QUERY LA DB");
        if (true) throw new RuntimeException("intentioant");
        return repo.findAll().stream().map(LanguageDto::new).collect(toList());

    }
}

//class ProxyGeneratDeSpring extends LanguageService {
//    @Override
//    public List<LanguageDto> findAll() {
//        // am in cace? return
//        return super.findAll();
//        // put in
//    }
//}
