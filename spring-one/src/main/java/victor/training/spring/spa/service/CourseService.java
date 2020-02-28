package victor.training.spring.spa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import victor.training.spring.spa.controller.dto.CourseDto;
import victor.training.spring.spa.domain.Course;
import victor.training.spring.spa.repo.CourseRepo;
import victor.training.spring.spa.repo.TeacherRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    public List<CourseDto> getAllCourses() {
        List<CourseDto> dtos = new ArrayList<CourseDto>();
        for (Course course : courseRepo.findAll()) {
            dtos.add(mapToDto(course));
        }
        return dtos;
    }

    public CourseDto getCourseById(Long id) {
        return mapToDto(courseRepo.findById(id).get());
    }

    public void updateCourse(Long id, CourseDto dto) throws ParseException {
        if (courseRepo.getByName(dto.name) != null &&  !courseRepo.getByName(dto.name).getId().equals(id)) {
            throw new IllegalArgumentException("Another course with that name already exists");
        }
        Course course = courseRepo.findById(id).get();
        course.setName(dto.name);
        course.setDescription(dto.description);
        // TODO implement date not in the past. i18n
        course.setStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(dto.startDate));
        course.setTeacher(teacherRepo.getOne(dto.teacherId));
    }

    public void deleteCourseById(Long id) {
        courseRepo.deleteById(id);
    }

    public void createCourse(CourseDto dto) throws ParseException {
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
        newEntity.setTeacher(teacherRepo.getOne(dto.teacherId));
        return newEntity;
    }
}
