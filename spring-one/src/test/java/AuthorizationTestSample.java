import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTestSample {

   @Autowired
   MockMvc mvc;

//   @WithMockUser(roles="ADMIN")
//   @WithMockUser(authorities = "training.delete", username = "user")
   @Test
   public void test() {
//       SecurityContextHolder.setContext(  security user cu teacher Ids inautru);
   }
}
