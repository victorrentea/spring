package victor.training.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.SecurityUser;
import victor.training.spring.web.controller.dto.CourseDto;
import victor.training.spring.web.domain.Course;
import victor.training.spring.web.repo.CourseRepo;
import victor.training.spring.web.repo.TeacherRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private EmailSender emailSender;

    public List<CourseDto> getAllCourses() {
        SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getManagedTeacherIds());
        List<CourseDto> dtos = new ArrayList<>();
        for (Course course : courseRepo.findAll()) {
            dtos.add(mapToDto(course));
        }
        return dtos;
    }

    public CourseDto getCourseById(Long id) {
        return mapToDto(courseRepo.findById(id).get());
    }

    // TODO Test this!
    public void updateCourse(Long id, CourseDto dto) throws ParseException {
        if (courseRepo.getByName(dto.name) != null &&  !courseRepo.getByName(dto.name).getId().equals(id)) {
            throw new IllegalArgumentException("Another course with that name already exists");
        }
        Course course = courseRepo.findById(id).get();
        course.setName(dto.name);
        course.setDescription(dto.description);
        // TODO implement date not in the past. i18n
        Date newDate = parseStartDate(dto);
        if (!newDate.equals(course.getStartDate())) {
            emailSender.sendScheduleChangedEmail(course.getTeacher(), course.getName(), newDate);
        }
        course.setStartDate(newDate);
        course.setTeacher(teacherRepo.getOne(dto.teacherId));
    }

    private Date parseStartDate(CourseDto dto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.parse(dto.startDate);
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
        newEntity.setStartDate(parseStartDate(dto));
        newEntity.setTeacher(teacherRepo.getOne(dto.teacherId));
        return newEntity;
    }
}
