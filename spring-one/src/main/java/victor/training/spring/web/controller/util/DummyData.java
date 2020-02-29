package victor.training.spring.web.controller.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.Course;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.repo.CourseRepo;
import victor.training.spring.web.repo.TeacherRepo;

@Service
public class DummyData {

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private TeacherRepo teacherRepo;

	@EventListener
	@Transactional
	public void initMockData(ContextRefreshedEvent event) {
		Course c1 = new Course("Spring Framework", "All about Spring", new Date(System.currentTimeMillis()+10*24*60*60*1000L));
		Course c2 = new Course("JPA", "The coolest standard in Java EE", new Date(System.currentTimeMillis()+2*24*60*60*1000L));
		Course c3 = new Course("AngularJS", "The new way of doing Single Page Applications", new Date(System.currentTimeMillis()+20*24*60*60*1000L));
		courseRepo.save(c1);
		courseRepo.save(c2);
		courseRepo.save(c3);
		
		Teacher t1 = new Teacher("Victor");
		Teacher t2 = new Teacher("Dragos");
		teacherRepo.save(t1);
		teacherRepo.save(t2);
		
		c1.setTeacher(t1);t1.getCourses().add(c1);
		c2.setTeacher(t1);t1.getCourses().add(c2);
		c3.setTeacher(t2);t2.getCourses().add(c3);
		
	}
	
	
}
