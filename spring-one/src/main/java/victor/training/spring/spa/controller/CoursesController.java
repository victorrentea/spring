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
@RequestMapping("rest/courses")
public class CoursesController {
	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public List<CourseDto> getAllCourses() {
		return courseService.getAllCourses();
	}

	@GetMapping("{id}")
	public CourseDto getCourseById(@PathVariable Long id) {
		return courseService.getCourseById(id);
	}

	@PutMapping("{id}")
	public void updateCourse(@PathVariable Long id,@RequestBody CourseDto dto) throws ParseException {
		courseService.updateCourse(id, dto);
	}

	@DeleteMapping("{id}")
	public void deleteCourseById(@PathVariable Long id) {
		courseService.deleteCourseById(id);
	}
	@PostMapping
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		courseService.createCourse(dto);
	}
}
