package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TrainingSearchRepo;

import javax.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class TrainingService {
    private final TrainingRepo trainingRepo;
    private final TrainingSearchRepo trainingSearchRepo;
    private final TeacherRepo teacherRepo;
    private final EmailSender emailSender;
    private final TeacherBioClient teacherBioClient;

    public List<TrainingDto> getAllTrainings() {
        List<TrainingDto> dtos = new ArrayList<>();
        for (Training training : trainingRepo.findAll()) {
            dtos.add(new TrainingDto(training));
        }
        return dtos;
    }

    public TrainingDto getTrainingById(Long id) {
        TrainingDto dto = new TrainingDto(trainingRepo.findById(id).orElseThrow());
        try {
            dto.teacherBio = teacherBioClient.retrieveBiographyForTeacher(dto.teacherId);
        } catch (RuntimeException e) {
            log.error("Error retrieving bio", e);
            dto.teacherBio = "<ERROR RETRIEVING TEACHER BIO (see logs)>";
        }
        return dto;
    }

    public void createTraining(TrainingDto dto) {
        if (trainingRepo.getByName(dto.name) != null) {
            throw new IllegalArgumentException("Another training with that name already exists");
        }
        Training newEntity = new Training()
                .setName(dto.name)
                .setDescription(dto.description)
                .setProgrammingLanguage(dto.language)
                .setStartDate(dto.startDate)
                .setTeacher(teacherRepo.getReferenceById(dto.teacherId));
        trainingRepo.save(newEntity);
    }

    // TODO update the training from the DTO
    // TODO report duplicated training name as an exception to the user
    // TODO implement optimistic locking (block 2 concurrent users to edit the same entity)
    // TODO emailSender.sendScheduleChangedEmail if the training changed the start date
    public void updateTraining(Long id, TrainingDto dto) {
        Training training= trainingRepo.findById(id).orElseThrow();
        training.setName(dto.name);
        training.setDescription(dto.description);
        training.setTeacher(teacherRepo.findById(dto.teacherId).orElseThrow());


    }

    public void deleteById(Long id) {
        trainingRepo.deleteById(id);
    }

    public List<TrainingDto> search(TrainingSearchCriteria criteria) {
        return trainingSearchRepo.search(criteria).stream()
            .map(TrainingDto::new)
            .collect(toList());
    }
}

