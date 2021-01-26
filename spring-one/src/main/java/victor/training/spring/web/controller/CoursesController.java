package victor.training.spring.web.controller;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.CourseDto;
import victor.training.spring.web.service.CourseService;

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
	public void updateCourse(@PathVariable Long id,@RequestBody CourseDto dto) throws ParseException {
		courseService.updateCourse(id, dto);
	}

	@DeleteMapping("{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	// DELETE COURSE FEATURE
	// TODO [SEC] Allow only for special permission
	@PreAuthorize("hasAuthority('deleteCourse')")
	public void deleteCourseById(@PathVariable Long id) {
//		helper.userHasRights(Features.DELETE_COURSE);
		courseService.deleteCourseById(id);
	}

	@PostMapping
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		courseService.createCourse(dto);
	}
}
