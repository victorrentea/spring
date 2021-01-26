package victor.training.spring.web.controller;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.CourseDto;
import victor.training.spring.web.domain.Course;
import victor.training.spring.web.repo.CourseRepo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.CourseService;

@Slf4j
@RestController
@RequestMapping("rest/courses")
@RequiredArgsConstructor
public class CoursesController {
	private final CourseService  courseService;

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
	// TODO [SEC] Allow only for special permission
	@PreAuthorize("hasAuthority('deleteCourse')")
//	@PreAuthorize("hasPermission()")
	public void deleteCourseById(@PathVariable Long id) {
		// CHALLENGE: "only the user that manages a teacher can delete one of that theacher's courses"

		Course course = repo.findById(id).get();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

		Set<Long> teacherIdsManagedByCurrentUser = securityUser.getManagedTeacherIds();
		log.info("Is teachedId:{} in {}?", course.getTeacher().getId(), teacherIdsManagedByCurrentUser);
		if (!teacherIdsManagedByCurrentUser.contains(course.getTeacher().getId())) {
			throw new IllegalArgumentException("Not Allowed");
		}

		courseService.deleteCourseById(id);
	}

	private final CourseRepo repo;

	@PostMapping
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		courseService.createCourse(dto);
	}
}
