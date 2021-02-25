package victor.training.spring.web.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

@SpringBootTest
@ActiveProfiles(value = {"db-mem"})
@Transactional
public abstract class RepoTestBase {
   @Autowired
   private TeacherRepo teacherRepo;
   protected Teacher teacher;
   @BeforeEach
   public void initialize() {
      teacher = teacherRepo.save(new Teacher());
   }

}
