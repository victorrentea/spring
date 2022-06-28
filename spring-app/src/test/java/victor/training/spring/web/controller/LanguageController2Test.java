package victor.training.spring.web.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class LanguageController2Test {

    @Autowired
    private LanguageController languageController;

    @Autowired
    LanguageService repo;

    @TestConfiguration
    static class MockCreatCaBean {
        @Bean
        public LanguageService languageService() {
            return mock(LanguageService.class);
        }
    }
    
    @Test
    void getAll() {
        languageController.getAll();
        languageController.getAll();

        verify(repo, Mockito.times(1))
                .getAll2();
    }
}