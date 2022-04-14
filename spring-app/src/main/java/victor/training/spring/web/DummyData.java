package victor.training.spring.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.entity.*;
import victor.training.spring.web.repo.TagRepo;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {
	private final TrainingRepo trainingRepo;
	private final TeacherRepo teacherRepo;
	private final TagRepo tagRepo;
	private final UserRepo userRepo;
	private final Environment environment;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		log.info("Inserting dummy data");
		Training spring = new Training("Spring Framework", "All about Spring", new Date(System.currentTimeMillis()+10*24*60*60*1000L));
		Training jpa = new Training("JPA", "The coolest standard in Java EE", new Date(System.currentTimeMillis()+2*24*60*60*1000L));
		Training javaBasic = new Training("Java Basic", "The new way of doing Single Page Applications", new Date(System.currentTimeMillis()+20*24*60*60*1000L));
		Training patterns = new Training("DesignPatterns", "Design Thinking", new Date(System.currentTimeMillis()+2*24*60*60*1000L));
		trainingRepo.save(spring);
		trainingRepo.save(jpa);
		trainingRepo.save(javaBasic);
		trainingRepo.save(patterns);

		Tag java = tagRepo.save(new Tag("Java"));
		Tag design = tagRepo.save(new Tag("Design"));
		Tag framework = tagRepo.save(new Tag("Framework"));

		javaBasic.addTags(java);
		jpa.addTags(java, framework);
		spring.addTags(java,design, framework);
		patterns.addTags(design);


		Teacher victor = new Teacher("Victor");
		victor.setContractType(ContractType.INDEPENDENT);
		Teacher ionut = new Teacher("Ionut");
		ionut.setContractType(ContractType.PART);

		teacherRepo.save(victor);
		teacherRepo.save(ionut);
		
		spring.setTeacher(victor);victor.getTrainings().add(spring);
		jpa.setTeacher(victor);victor.getTrainings().add(jpa);
		javaBasic.setTeacher(ionut);ionut.getTrainings().add(javaBasic);
		patterns.setTeacher(victor);victor.getTrainings().add(patterns);

		userRepo.save(new UserEntity("Boss", "admin", UserRole.ADMIN, Arrays.asList(victor.getId()))); // only manages Victor, not Ionut
		userRepo.save(new UserEntity("Clerk", "user", UserRole.USER, Arrays.asList(victor.getId(), ionut.getId())));
		userRepo.flush();
		log.info(">>> Spring-One Application started on {} <<<", environment.getProperty("local.server.port"));
	}

	
}
