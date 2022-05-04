package victor.training.spring.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import victor.training.spring.web.entity.*;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class DummyData /*implements CommandLineRunner*/ {
	private final TrainingRepo trainingRepo;
	private final TeacherRepo teacherRepo;
	private final ProgrammingLanguageRepo languageRepo;
	private final UserRepo userRepo;
	private final Environment environment;

//	@Override
//	@Transactional
	@PostConstruct
	public void run() throws Exception {
		log.info("Inserting dummy data");

		ProgrammingLanguage java = languageRepo.save(new ProgrammingLanguage("Java"));
		ProgrammingLanguage php = languageRepo.save(new ProgrammingLanguage("PHP"));

		Teacher victor = teacherRepo.save(new Teacher("Victor").setContractType(ContractType.INDEPENDENT));
		Teacher ionut = teacherRepo.save(new Teacher("Ionut").setContractType(ContractType.PART));

		Training spring = new Training("Spring Framework", new Date(System.currentTimeMillis()+10*24*60*60*1000L))
				.setDescription("All about Spring")
				.setProgrammingLanguage(java)
				.setTeacher(victor);
		Training jpa = new Training("JPA", new Date(System.currentTimeMillis()+2*24*60*60*1000L))
				.setDescription("The coolest standard in Java EE")
				.setProgrammingLanguage(java)
				.setTeacher(victor);
		Training javaBasic = new Training("Java Basic", new Date(System.currentTimeMillis()+20*24*60*60*1000L))
				.setDescription("The new way of doing Single Page Applications")
				.setProgrammingLanguage(java)
				.setTeacher(ionut);
		Training patterns = new Training("Design Patterns", new Date(System.currentTimeMillis()+2*24*60*60*1000L))
				.setDescription("Design Thinking")
				.setProgrammingLanguage(php)
				.setTeacher(victor);

		trainingRepo.save(spring);
		trainingRepo.save(jpa);
		trainingRepo.save(javaBasic);
		trainingRepo.save(patterns);

		userRepo.save(new User("Boss", "admin", UserRole.ADMIN, Arrays.asList(victor.getId()))); // only manages Victor, not Ionut
		userRepo.save(new User("Clerk", "user", UserRole.USER, Arrays.asList(victor.getId(), ionut.getId())));

		log.info(">>> Spring-One Application started on {} <<<", environment.getProperty("local.server.port"));
	}

	
}
