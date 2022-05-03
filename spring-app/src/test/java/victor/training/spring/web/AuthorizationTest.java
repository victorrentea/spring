//package victor.training.spring.web;
//
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import victor.training.spring.web.service.TrainingService;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoInteractions;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("spa")
//public class AuthorizationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TrainingService trainingService;
//
//    @WithMockUser
//    @Test
//    public void testSearch() throws Exception {
//        mockMvc.perform(delete("/api/trainings/1"))
//            .andExpect(status().is(Matchers.not(200)));
//        verifyNoInteractions(trainingService);
//    }
//
//    @WithMockUser(roles = "training.delete")
//    @Test
//    public void testSearchOK() throws Exception {
//        mockMvc.perform(delete("/api/trainings/1"))
//            .andExpect(status().is2xxSuccessful());
//        verify(trainingService).deleteById(1L);
//    }
//
//}
