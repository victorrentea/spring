package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.service.TeacherService;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teachers")
public class TeachersController {
  private final TeacherService service;

  @GetMapping
  public List<TeacherDto> getAllTeachers() {
    return service.getAllTeachers();
  }

  @GetMapping("create") // easier to demo in BROWSER
  public void createTeacherFromBrowser() {
    TeacherDto dto = new TeacherDto();
    dto.name = "Teacher" + LocalDateTime.now();
    createTeacher(dto);
  }

  @PostMapping
  public void createTeacher(
      @Valid @RequestBody TeacherDto dto) {
    service.createTeacher(dto);
  }

  //	@PutMapping
  //	public void createTeacher(@Valid @RequestBody TeacherDto dto) {
  @GetMapping("{id}/update") // easier to demo in BROWSER
  public void updateTeacher(@PathVariable long id) {
    String newName = "Teacher" + LocalDateTime.now();
    service.updateTeacher(id, newName);
  }
}
