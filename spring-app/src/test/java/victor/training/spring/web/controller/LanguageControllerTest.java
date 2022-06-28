//package victor.training.spring.web.controller;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import victor.training.spring.web.repo.ProgrammingLanguageRepo;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//
//@SpringBootTest
//class LanguageControllerTest {
//
//    @Autowired
//    private LanguageController languageController;
//
//    @MockBean
//    ProgrammingLanguageRepo repo;
//
//    @Test
//    void getAll() {
//        languageController.getAll();
//        languageController.getAll();
//
//        verify(repo, Mockito.times(1))
//                .findAll();
//    }
//}