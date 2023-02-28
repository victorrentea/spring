package victor.training.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.web.entity.*;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

import static victor.training.spring.web.entity.ProgrammingLanguage.*;

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


		Teacher victor = teacherRepo.save(new Teacher("Victor").setContractType(ContractType.INDEPENDENT));
		Teacher ionut = teacherRepo.save(new Teacher("Ionut").setContractType(ContractType.PART));

		Training spring = new Training("Spring Framework", new Date(System.currentTimeMillis()+10*24*60*60*1000L))
				.setDescription("<p>All about <b>Spring</b></p>")
				.setProgrammingLanguage(JAVA)
				.setTeacher(victor);
		Training jpa = new Training("JPA", new Date(System.currentTimeMillis()+2*24*60*60*1000L))
				.setDescription("<p>The coolest standard in Java EE</p>")
				.setProgrammingLanguage(JAVA)
				.setTeacher(victor);
		Training javaBasic = new Training("Java Basic", new Date(System.currentTimeMillis()+20*24*60*60*1000L))
				.setDescription("<p>The new way of doing Single Page Applications</p>")
				.setProgrammingLanguage(JAVA)
				.setTeacher(ionut);
		Training patterns = new Training("Design Patterns", new Date(System.currentTimeMillis()+2*24*60*60*1000L))
				.setDescription("<p>Design Thinking</p>")
				.setProgrammingLanguage(PHP)
				.setTeacher(victor);

		trainingRepo.save(spring);
		trainingRepo.save(jpa);
		trainingRepo.save(javaBasic);
		trainingRepo.save(patterns);

		userRepo.save(new User("PowerHorse", "power", UserRole.POWER, List.of(victor.getId()))); // only manages Victor, not Ionut
		userRepo.save(new User("Za Boss", "admin", UserRole.ADMIN, List.of(victor.getId()))); // only manages Victor, not Ionut
		userRepo.save(new User("Clerk", "user", UserRole.USER, List.of(victor.getId(), ionut.getId())));
	}


}
