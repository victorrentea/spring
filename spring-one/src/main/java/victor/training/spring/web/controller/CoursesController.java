package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.CourseDto;
import victor.training.spring.web.service.CourseService;

public class CoursesController {
	@Autowired
	private CourseService courseService;
	
	// TODO [SEC] Restrict display for courses of teachers of users
	public List<CourseDto> getAllCourses() {
		return courseService.getAllCourses();
	}

	// TODO [SEC] Check user manages teacher of this course
	public CourseDto getCourseById(Long id) {
		return courseService.getCourseById(id);
	}

	// TODO [SEC] Check user manages teacher of this course
	public void updateCourse(Long id,CourseDto dto) throws ParseException {
		courseService.updateCourse(id, dto);
	}

	// TODO [SEC] Allow only for special permission
	public void deleteCourseById(Long id) {
		courseService.deleteCourseById(id);
	}

	public void createCourse(CourseDto dto) throws ParseException {
		courseService.createCourse(dto);
	}
}
