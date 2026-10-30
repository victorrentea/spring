package victor.training.spring;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.web.entity.*;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import java.time.LocalDate;
import java.util.List;

import static victor.training.spring.web.entity.ProgrammingLanguage.JAVA;
import static victor.training.spring.web.entity.ProgrammingLanguage.PHP;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitialData {
  private final TrainingRepo trainingRepo;
  private final TeacherRepo teacherRepo;
  private final UserRepo userRepo;

  @PostConstruct
  public void run() throws Exception {
    log.info("Inserting initial training data");


    Teacher victor = teacherRepo.save(new Teacher("Victor").contractType(ContractType.INDEPENDENT));
    Teacher ionut = teacherRepo.save(new Teacher("Ionut").contractType(ContractType.PART));

    Training spring = new Training("Spring Framework", LocalDate.now().plusDays(10))
            .description("<p>All about <b>Spring</b></p>")
            .programmingLanguage(JAVA)
            .teacher(victor);
    Training jpa = new Training("JPA", LocalDate.now().plusDays(2))
            .description("<p>The coolest standard in Java EE</p>")
            .programmingLanguage(JAVA)
            .teacher(victor);
    Training javaBasic = new Training("Java Basic", LocalDate.now().plusDays(20))
            .description("<p>The new way of doing Single Page Applications</p>")
            .programmingLanguage(JAVA)
            .teacher(ionut);
    Training patterns = new Training("Design Patterns", LocalDate.now().plusDays(5))
            .description("<p>Design Thinking</p>")
            .programmingLanguage(PHP)
            .teacher(victor);

    trainingRepo.save(spring);
    trainingRepo.save(jpa);
    trainingRepo.save(javaBasic);
    trainingRepo.save(patterns);

    userRepo.save(new User("PowerHorse", "power", UserRole.POWER, List.of(victor.id()), PHP)); // only manages Victor, not Ionut
    userRepo.save(new User("Za Boss", "admin", UserRole.ADMIN, List.of(victor.id()), JAVA)); // only manages Victor, not Ionut
    userRepo.save(new User("Clerk", "user", UserRole.USER, List.of(victor.id(), ionut.id()), null));
  }


}
