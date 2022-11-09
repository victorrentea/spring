package victor.training.spring.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
	public List<TeacherDto> getAllTeachers() {
		return service.getAllTeachers();
	}

	@GetMapping("evict-cache")
	@CacheEvict("profi")
	public void killCache() {
		// nimic aici. da' n-o sterge, let the magic hapen !
	}
}
