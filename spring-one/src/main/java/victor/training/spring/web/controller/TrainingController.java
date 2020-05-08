package victor.training.spring.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.service.TrainingService;

public class TrainingController {
	@Autowired
	private TrainingService trainingService;
	
	// TODO [SEC] Restrict display for courses of teachers of users
	public List<TrainingDto> getAllCourses() {
		return trainingService.getAllCourses();
	}

	// TODO [SEC] Check user manages teacher of this course
	public TrainingDto getCourseById(Long id) {
		return trainingService.getCourseById(id);
	}

	// TODO [SEC] Check user manages teacher of this course
	public void updateCourse(Long id, TrainingDto dto) throws ParseException {
		trainingService.updateCourse(id, dto);
	}

	// after switching to DatabaseUserDetailsService
	// TODO [SEC] 1 Allow only for ROLE 'USER'
	// TODO [SEC] 2 Authorize the user to have the authority 'deleteCourse'
	/** @see victor.training.spring.web.domain.UserProfile */
	public void deleteCourseById(Long id) {
		trainingService.deleteCourseById(id);
	}

	public void createCourse(TrainingDto dto) throws ParseException {
		trainingService.createCourse(dto);
	}
}
