package victor.training.spring.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.service.WithReferenceData;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("db-mem")
@Transactional
class TrainingControllerTest {
   @Autowired
   MockMvc  mockMvc;

   @WithReferenceData
   @Test
   void createTraining() throws Exception {
      mockMvc.perform(post("/rest/trainings")
          .content("{\n" +
                   "  \"name\": \"training1\",\n" +
                   "  \"teacherId\": 1,\n" +
                   "  \"startDate\": \"01-12-2020\"\n" +
                   "}")
          .contentType(MediaType.APPLICATION_JSON)
      )
          .andExpect(status().isOk());

//      mockMvc.perform(get("/rest/trainings"))
//          .andExpect(MockMvcResultMatchers.content().string())
   }

}