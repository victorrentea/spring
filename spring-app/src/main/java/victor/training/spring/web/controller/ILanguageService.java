package victor.training.spring.web.controller;

import org.springframework.cache.annotation.Cacheable;
import victor.training.spring.web.controller.dto.LanguageDto;

import java.util.List;

public interface ILanguageService {
    @Cacheable("countries")
    List<LanguageDto> getLanguages();
}
