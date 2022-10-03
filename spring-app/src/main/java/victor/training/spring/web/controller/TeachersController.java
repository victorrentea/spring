package victor.training.spring.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.service.TeacherService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/teachers")
public class TeachersController {
	@Autowired
	private TeacherService service;
	
	@GetMapping
//	@Cacheable("all-teachers")
	public List<TeacherDto> getAllTeachers() {
		return service.getAllTeachers();
	}

	//	@PostMapping
	//	public void createTeacher(@Valid @RequestBody TeacherDto dto) {
	@GetMapping("create") // easier to demo in BROWSER
//	@CacheEvict("all-teachers")
	public void createTeacher() {
		TeacherDto dto = new TeacherDto();
		dto.name = "Teacher" + LocalDateTime.now();
		service.createTeacher(dto);
	}

//	@CacheEvict("all-teachers")
	public void updateTeacher() {

	}

	//	@PutMapping
	//	public void createTeacher(@Valid @RequestBody TeacherDto dto) {
	@GetMapping("{id}/update") // easier to demo in BROWSER
	public void updateTeacher(@PathVariable long id) {
		String newName = "Teacher" + LocalDateTime.now();
		service.updateTeacher(id, newName);
	}
}
