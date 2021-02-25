package victor.training.spring.web.service;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

public class WithCountryRefData implements BeforeEachCallback {
   private final Teacher teacher = new Teacher();

   public Teacher getTeacher() {
      return teacher;
   }

   @Override
   public void beforeEach(ExtensionContext context) throws Exception {
      TeacherRepo repo = SpringExtension.getApplicationContext(context).getBean(TeacherRepo.class);
      System.out.println("******** Insert Common data via entity manager: " + repo);
      repo.save(teacher);
   }
}
