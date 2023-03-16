package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TrainingSearchRepo;

import javax.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;

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
        Training training = trainingRepo.findById(id).orElseThrow();
        TrainingDto dto = new TrainingDto(training);
        dto.teacherBio = retrieveTeacherBio(dto.teacherId);
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        training.startEdit(currentUser); // PESSIMISTIC LOCKING
        return dto;
    }

    private String retrieveTeacherBio(Long teacherId) {
        try {
            return teacherBioClient.retrieveBiographyForTeacher(teacherId);
        } catch (RuntimeException e) {
            log.error("Error retrieving bio", e);
            return "<ERROR RETRIEVING TEACHER BIO (see logs)>";
        }
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

    public void updateTraining(TrainingDto dto) {
        if (trainingRepo.countByNameAndIdNot(dto.name, dto.id) != 0) {
            throw new IllegalArgumentException("Another training with that name already exists");
        }
        Training training = trainingRepo.findById(dto.id).orElseThrow()
                .setName(dto.name)
                .setDescription(dto.description)
                .setProgrammingLanguage(dto.language)
                .setTeacher(new Teacher(dto.teacherId));

        if (!dto.startDate.equals(training.getStartDate())) {
            // TODO check date not in the past
            emailSender.sendScheduleChangedEmail(training.getTeacher(), training.getName(), dto.startDate);
            training.setStartDate(dto.startDate);
        }

        // OPTIMISTIC LOCKING
        if (!dto.version.equals(training.getVersion())) {
//            throw new OptimisticLockException("Another user changed the entity in the meantime. Please refresh the page and re-do your changes.");
            // Alternative: Hibernate would throw this automatically when repo.save(new Entity from Dto) => EntityManager.merge operator
        }
        // PESSIMISTIC LOCKING
        //training.finishEdit(SecurityContextHolder.getContext().getAuthentication().getName());
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

