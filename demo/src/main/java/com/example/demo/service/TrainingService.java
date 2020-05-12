package com.example.demo.service;

import com.example.demo.DemoException;
import com.example.demo.dto.TrainingDto;
import com.example.demo.entity.Training;
import com.example.demo.repo.TeacherRepo;
import com.example.demo.repo.TrainingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private TrainingMapper trainingMapper;

    public List<TrainingDto> getAllTrainings() {
        return trainingRepo.findAll().stream().map(training -> new TrainingDto(training)).collect(toList());
    }

    public TrainingDto getTrainingById(Long id) {

        return new TrainingDto(trainingRepo.findById(id).get());
    }

    // TODO Test this!
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null &&  !trainingRepo.getByName(dto.name).getId().equals(id)) {
            throw new DemoException(DemoException.ErrorCode.DUPLICATE_NAME, dto.name);
        }
        Training training = trainingRepo.findById(id).get();
        training.setName(dto.name);
        training.setDescription(dto.description);
        training.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ISO_DATE));
        training.setTeacher(teacherRepo.getOne(dto.teacherId));
    }

    public void deleteTrainingById(Long id) {
        trainingRepo.deleteById(id);
    }

    public void createTraining(TrainingDto dto) {
        if (trainingRepo.getByName(dto.name) != null) {
            throw new IllegalArgumentException("Another course with that name already exists");
        }
        Training newEntity = trainingMapper.mapToEntity(dto);
        trainingRepo.save(newEntity);
    }


}
