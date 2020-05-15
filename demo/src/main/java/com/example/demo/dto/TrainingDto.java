package com.example.demo.dto;

import com.example.demo.entity.Training;
import com.example.demo.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TrainingDto implements Serializable {
    public Long id;
    public String name;
    public Long teacherId;
    public String teacherName;
    public String startDate;
    public String description;
//    @Autowired NU MERGE IN CLASE DE DATE > JACKSON O CREEAZA nu SPRING
//    private TeacherRepo teacherRepo;

    public TrainingDto() {}
    public TrainingDto(Training training) {
        name = training.getName();
//        teacherName = training.getTeacher().map(Teacher::getName).orElse("");
        teacherName = training.getTeacher().getName();
        id = training.getId();
        startDate = training.getStartDate().map(d -> d.format(DateTimeFormatter.ISO_DATE)).orElse("");
        description = training.getDescription();
//        teacherId = training.getTeacher().map(Teacher::getId).orElse(null);
        teacherId = training.getTeacher().getId();
    }

//    public Training toEntity() {
//        Training newEntity = new Training();
//        newEntity.setName(name);
//        newEntity.setDescription(description);
//        newEntity.setStartDate(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE));
//        newEntity.setTeacher(teacherRepo.getOne(teacherId));
//        return newEntity;
//    }
}
