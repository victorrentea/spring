package victor.training.spring.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.service.TeacherService;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	public void createTeacher(@Valid @RequestBody TeacherDto dto) {
		service.createTeacher(dto);
	}
	@PostMapping("upload")
	public void upload(MultipartFile file) throws IOException { // vine fisier de 200MB pe request
		File tempFile  = null;//new File(folderTemp, fis+UUID);
		try (OutputStream os = new FileOutputStream(tempFile)) {
			IOUtils.copy(file.getInputStream(), os);
		}
		service.process(tempFile);
	}


	@DeleteMapping(value = "/{id}")
	public void deleteTeacher(@PathVariable("id") Long id) {
		service.deleteTeacher(id);
	}

}
