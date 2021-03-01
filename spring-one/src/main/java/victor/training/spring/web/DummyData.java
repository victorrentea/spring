package victor.training.spring.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.domain.UserProfile;
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
	private final UserRepo userRepo;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		log.info("Inserting dummy data");
		Training c1 = new Training("Spring Framework", "All about Spring", new Date(System.currentTimeMillis()+10*24*60*60*1000L));
		Training c2 = new Training("JPA", "The coolest standard in Java EE", new Date(System.currentTimeMillis()+2*24*60*60*1000L));
		Training c3 = new Training("Java Basic", "The new way of doing Single Page Applications", new Date(System.currentTimeMillis()+20*24*60*60*1000L));
		Training c4 = new Training("DesignPatterns", "Design Thinking", new Date(System.currentTimeMillis()+2*24*60*60*1000L));
		trainingRepo.save(c1);
		trainingRepo.save(c2);
		trainingRepo.save(c3);
		trainingRepo.save(c4);

		Teacher victor = new Teacher("Victor");
		Teacher ionut = new Teacher("Ionut");
		teacherRepo.save(victor);
		teacherRepo.save(ionut);
		
		c1.setTeacher(victor);victor.getTrainings().add(c1);
		c2.setTeacher(victor);victor.getTrainings().add(c2);
		c3.setTeacher(ionut);ionut.getTrainings().add(c3);
		c4.setTeacher(victor);victor.getTrainings().add(c4);

		userRepo.save(new User("admin", UserProfile.ADMIN, Arrays.asList(victor.getId()))); // only manages Victor, not Ionut
		userRepo.save(new User("user", UserProfile.USER, Arrays.asList(victor.getId(), ionut.getId())));
	}
	
	
}
