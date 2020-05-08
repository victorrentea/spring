package com.example.demo.service;

import com.example.demo.dto.TrainingDto;
import com.example.demo.entity.Training;
import com.example.demo.repo.TeacherRepo;
import com.example.demo.repo.TrainingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TeacherRepo teacherRepo;

    public List<TrainingDto> getAllTrainings() {
        // TODO
        return Collections.emptyList();
    }

    public TrainingDto getTrainingById(Long id) {
        return mapToDto(trainingRepo.findById(id).get());
    }

    // TODO Test this!
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null &&  !trainingRepo.getByName(dto.name).getId().equals(id)) {
            throw new IllegalArgumentException("Another course with that name already exists");
        }
        Training training = trainingRepo.findById(id).get();
        training.setName(dto.name);
        training.setDescription(dto.description);
        training.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        training.setTeacher(teacherRepo.getOne(dto.teacherId));
    }

    public void deleteTrainingById(Long id) {
        trainingRepo.deleteById(id);
    }

    public void createTraining(TrainingDto dto) {
        if (trainingRepo.getByName(dto.name) != null) {
            throw new IllegalArgumentException("Another course with that name already exists");
        }
        Training newEntity = new Training();
        // TODO
        trainingRepo.save(newEntity);
    }

    private TrainingDto mapToDto(Training training) {
        TrainingDto dto = new TrainingDto();
        // TODO
        return dto ;
    }

}
