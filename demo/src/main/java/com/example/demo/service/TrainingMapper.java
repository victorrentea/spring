package com.example.demo.service;

import com.example.demo.dto.TrainingDto;
import com.example.demo.entity.Training;
import com.example.demo.repo.TeacherRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TrainingMapper {
    private final TeacherRepo teacherRepo;
    public TrainingMapper(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public Training mapToEntity(TrainingDto dto) {
        Training newEntity = new Training();
        newEntity.setName(dto.name);
        newEntity.setDescription(dto.description);
        newEntity.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ISO_DATE));
        newEntity.setTeacher(teacherRepo.getOne(dto.teacherId));
        return newEntity;
    }
}
