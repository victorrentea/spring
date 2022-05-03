package victor.training.spring.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.service.TeacherService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/teachers")
public class TeachersController {
	@Autowired
	private TeacherService service;

	@PostConstruct
	public void method() {
		System.out.println("Oare cu ce service vorbesc ?" + service.getClass());
	}
	
	@GetMapping
	public List<TeacherDto> getAllTeachers() {
		return service.getAllTeachers();
	}
	@GetMapping("{id}")
	public TeacherDto getTeacherById(@PathVariable long id) {
		return service.getTeacherById(id);
	}

	//	@PostMapping
	//	public void createTeacher(@Valid @RequestBody TeacherDto dto) {
	@GetMapping("create") // easier to demo in BROWSER
	public void createTeacher() {
		TeacherDto dto = new TeacherDto();
		dto.name = "Teacher" + LocalDateTime.now();
		service.createTeacher(dto);
	}

	//	@PutMapping
	//	public void createTeacher(@Valid @RequestBody TeacherDto dto) {
	@GetMapping("{id}/update") // easier to demo in BROWSER
	public void updateTeacher(@PathVariable long id) {
		String newName = "Teacher" + LocalDateTime.now();
		service.updateTeacher(id, newName);
	}

	@GetMapping("by-contract/{contractType}")
	public List<TeacherDto> getTeacherByContractType(@PathVariable ContractType contractType) {
		return service.getTeacherByContractType(contractType);
	}

}
