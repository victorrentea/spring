package victor.training.spring.spa.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import victor.training.spring.spa.controller.dto.TeacherDto;
import victor.training.spring.spa.domain.Teacher;
import victor.training.spring.spa.service.TeacherService;

@RestController
@RequestMapping("/rest/teachers")
public class TeachersController {
	@Autowired
	private TeacherService service;
	
	@GetMapping
	public List<TeacherDto> getAllTeachers() {
		return service.getAllTeachers();
	}

	@PostMapping
	public void createTeacher(@RequestBody TeacherDto dto) {
		service.createTeacher(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteTeacher(@PathVariable("id") Long id) {
		service.deleteTeacher(id);
	}

}
