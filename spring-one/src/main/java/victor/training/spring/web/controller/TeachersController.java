package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.service.TeacherService;

import java.util.List;


@RestController
@RequestMapping("/api/teachers")
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
