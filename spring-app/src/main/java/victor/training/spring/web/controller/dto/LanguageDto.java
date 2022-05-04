package victor.training.spring.web.controller.dto;

import victor.training.spring.web.entity.ProgrammingLanguage;

public class LanguageDto {
    public long id;
    public String name;

    public LanguageDto(ProgrammingLanguage programmingLanguage) {
        id = programmingLanguage.getId();
        name = programmingLanguage.getName();
    }
}
