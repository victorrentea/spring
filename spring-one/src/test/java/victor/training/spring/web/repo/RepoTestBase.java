package victor.training.spring.web.repo;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.domain.UserProfile;

import java.util.Collections;

//Pt @Andrei
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:config.xml"})


@SpringBootTest
//@RunWith(SpringRunner.class) // pt junit4
@ActiveProfiles({"db-mem","test"})
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
//@CleanupData
@Transactional
public abstract class RepoTestBase {
   @Autowired
   private UserRepo userRepo;
   protected User user;

   @BeforeEach
   public void persistStaticData() {
      user = userRepo.save(new User("user", UserProfile.USER, Collections.emptyList()));

   }
}
