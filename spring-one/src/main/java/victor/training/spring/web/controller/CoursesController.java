package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.CourseDto;
import victor.training.spring.web.domain.Course;
import victor.training.spring.web.repo.CourseRepo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.CourseService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("rest/courses")
public class CoursesController {
	@Autowired
	private CourseService courseService;
	
	// TODO [SEC] Restrict display for courses of teachers of users
	@GetMapping
	public List<CourseDto> getAllCourses() {
		return courseService.getAllCourses();
	}

	// TODO [SEC] Check user manages teacher of this course
	@GetMapping("{id}")
	public CourseDto getCourseById(@PathVariable Long id) {
		return courseService.getCourseById(id);
	}

	@PutMapping("{id}")
	// TODO [SEC] Check user manages teacher of this course
		public void updateCourse(@PathVariable Long id, @RequestBody CourseDto dto) throws ParseException {
		courseService.updateCourse(id, dto);
	}

	@DeleteMapping("{id}")
	// TODO [SEC] Allow only for special permission
	// TODO  and @accessController.canDeleteCourse(#id)
	public void deleteCourseById(@PathVariable Long id) {
		courseService.deleteCourseById(id);
	}

	@PostMapping
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		courseService.createCourse(dto);
	}
}
