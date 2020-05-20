package com.example.demo;

import com.example.demo.entity.Teacher;
import com.example.demo.entity.Training;
import com.example.demo.entity.User;
import com.example.demo.repo.TeacherRepo;
import com.example.demo.repo.TrainingRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Profile("!test")
@Service
public class DummyData {

	@Autowired
	private TrainingRepo trainingRepo;

	@Autowired
	private TeacherRepo teacherRepo;


	@EventListener
	@Transactional
	public void initMockData(ContextRefreshedEvent event) {
		System.out.println("Inserting dummy data");
		Teacher t1 = new Teacher("Victor");
		Teacher t2 = new Teacher("Ionut");
		teacherRepo.save(t1);
		teacherRepo.save(t2);


		Training c1 = new Training("Spring Framework", "All about Spring", LocalDate.now(), t1);
		Training c2 = new Training("JPA", "The coolest standard in Java EE", LocalDate.now(), t1);
		Training c3 = new Training("Java Basic", "The new way of doing Single Page Applications", LocalDate.now(), t2);
		Training c4 = new Training("DesignPatterns", "Design Thinking", LocalDate.now(), t1);
		trainingRepo.save(c1);
		trainingRepo.save(c2);
		trainingRepo.save(c3);
		trainingRepo.save(c4);


		userRepo.save(new User("user", "John", User.Role.USER, Arrays.asList(t1.getId())));
		userRepo.save(new User("admin", "Guru", User.Role.ADMIN, Arrays.asList(t1.getId(), t2.getId())));
	}

	@Autowired
	private UserRepo userRepo;

}
