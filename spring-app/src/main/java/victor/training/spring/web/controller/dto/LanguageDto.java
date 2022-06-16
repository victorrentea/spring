package victor.training.spring.web.controller.dto;

import victor.training.spring.web.entity.ProgrammingLanguage;

import java.io.Serializable;

public class LanguageDto implements Serializable {
    public long id;
    public String name;

    public LanguageDto(ProgrammingLanguage programmingLanguage) {
        id = programmingLanguage.getId();
        name = programmingLanguage.getName();
    }
}
