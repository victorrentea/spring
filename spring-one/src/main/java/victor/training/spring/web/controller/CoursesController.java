package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
	@PreAuthorize("hasAuthority('deleteCourse')")
	// TODO [SEC] Allow only for special permission
	public void deleteCourseById(@PathVariable Long id) {
		if (!canDeleteCourse(id)) {
			throw new IllegalArgumentException("No access!");
		}
		courseService.deleteCourseById(id);
	}

	@Autowired
	private CourseRepo courseRepo;

	private boolean canDeleteCourse(long courseId) {
		SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Course course = courseRepo.findById(courseId).get();
		Set<Long> managedTeacherIds = securityUser.getManagedTeacherIds();
		log.info("Managed teacher IDs: " + managedTeacherIds);
		log.info("Course . teacher.id " + course.getTeacher().getId());
		return managedTeacherIds.contains(course.getTeacher().getId());

	}

	@PostMapping
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		courseService.createCourse(dto);
	}
}
