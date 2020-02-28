package victor.training.spring.spa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import victor.training.spring.spa.controller.dto.CourseDto;
import victor.training.spring.spa.domain.Course;
import victor.training.spring.spa.repo.CourseRepo;
import victor.training.spring.spa.repo.TeacherRepo;
import victor.training.spring.spa.controller.util.DummyData;
import victor.training.spring.spa.service.CourseService;

@RestController
public class CoursesController {
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/rest/courses")
	public List<CourseDto> getAllCourses() {
		return courseService.getAllCourses();
	}

	@GetMapping("/rest/courses/{id}")
	public CourseDto getCourseById(@PathVariable Long id) {
		return courseService.getCourseById(id);
	}

	@PutMapping("rest/courses/{id}")
	public void updateCourse(@PathVariable Long id,@RequestBody CourseDto dto) throws ParseException {
		courseService.updateCourse(id, dto);
	}
	
	public void deleteCourseById(Long id) {
		courseService.deleteCourseById(id);
	}
	
	public void createCourse(CourseDto dto) throws ParseException { 
		createCourse(dto);
	}
}
