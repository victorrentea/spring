package victor.training.spring.web;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import victor.training.spring.web.service.TrainingService;

import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private TrainingService trainingService;

  @WithMockUser(authorities = "training.delete")
  @Disabled("if needed")
  @Test
  public void userCanDelete() throws Exception {
    mockMvc.perform(delete("/api/trainings/1/delete")).andExpect(status().is2xxSuccessful());
    verify(trainingService).deleteById(1L);
  }

  @WithMockUser(authorities = "..")
  @Test
  public void userCannotDelete() throws Exception {
    mockMvc.perform(delete("/api/trainings/1/delete")).andExpect(status().is(not(200)));
    verifyNoInteractions(trainingService);
  }


}
