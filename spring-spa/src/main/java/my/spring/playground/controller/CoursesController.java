package my.spring.playground.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import my.spring.playground.SpaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import my.spring.playground.controller.dto.CourseDto;
import my.spring.playground.domain.Course;
import my.spring.playground.repo.CourseRepository;
import my.spring.playground.repo.TeacherRepository;
import my.spring.playground.service.DummyData;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("rest/courses")
public class CoursesController {
	
	private final static Logger log = LoggerFactory.getLogger(CoursesController.class);

	@Autowired
	private DummyData service;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;

	@GetMapping
	public List<CourseDto> getAllCourses() {
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (Course course : courseRepo.findAll()) {
			dtos.add(mapToDto(course));
		}
		return dtos;
	}

	@GetMapping("{id}")
	public CourseDto getCourseById(@PathVariable Long id) {
		return mapToDto(courseRepo.getById(id));
	}

	@PutMapping("{id}")
	public void updateCourse(@PathVariable Long id, @RequestBody CourseDto dto) throws ParseException {
		if (courseRepo.getByName(dto.name) != null &&  !courseRepo.getByName(dto.name).getId().equals(id)) {
			throw new IllegalArgumentException("Another course with that name already exists");
		}
		Course course = courseRepo.getById(id);
		course.setName(dto.name);
		course.setDescription(dto.description);

		Date newDate = parseDate(dto.startDate);
		if (newDate.before(new Date())) {
			throw new SpaException(SpaException.ErrorCode.COURSE_DATE_IN_THE_PAST);
		}
		course.setStartDate(newDate);
		course.setTeacher(teacherRepo.getById(dto.teacherId));
	}

	private Date parseDate(String startDateStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		format.setLenient(false);
		return format.parse(startDateStr);
	}

	@DeleteMapping("{id}")
	public void deleteCourseById(@PathVariable Long id) {
		courseRepo.deleteById(id);
	}

	@PostMapping
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		if (courseRepo.getByName(dto.name) != null) {
			throw new IllegalArgumentException("Another course with that name already exists");
		}
		courseRepo.save(mapToEntity(dto));
	}

	private CourseDto mapToDto(Course course) {
		CourseDto dto = new CourseDto();
		dto.id = course.getId();
		dto.name = course.getName();
		dto.description = course.getDescription();
		dto.startDate = new SimpleDateFormat("dd-MM-yyyy").format(course.getStartDate());
		dto.teacherId = course.getTeacher().getId();
		dto.teacherName = course.getTeacher().getName();
		return dto ;
	}
	
	private Course mapToEntity(CourseDto dto) throws ParseException {
		Course newEntity = new Course();
		newEntity.setName(dto.name);
		newEntity.setDescription(dto.description);
		newEntity.setStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(dto.startDate));
		newEntity.setTeacher(teacherRepo.getById(dto.teacherId));
		return newEntity;
	}
	
	
}
