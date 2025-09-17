package victor.training.spring.web.service;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.aspects.Logged;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TrainingSearchRepo;

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

  private final ApplicationContext spring;

  @Cacheable("cache-name") // Reminder: nu folosi
  @Timed
  @Secured({"ROLE_USER","ROLE_ADMIN"})
  public List<TrainingDto> getAllTrainings() {
//    var trainingRepo = spring.getBean(TrainingRepo.class);
    List<TrainingDto> dtos = new ArrayList<>();
    for (Training training : trainingRepo.findAll()) {
      dtos.add(new TrainingDto(training));
    }
    new RuntimeException("nearuncata").printStackTrace();
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
      return "<ERROR RETRIEVING TEACHER BIO: " + e + ">";
    }
  }

  public void createTraining(TrainingDto dto) {
    if (trainingRepo.getByName(dto.name) != null) {
      throw new IllegalArgumentException("Another training with that name already exists");
    }
    Training newTraining = updateTrainingFields(dto, new Training())
        .setStartDate(dto.startDate);
    trainingRepo.save(newTraining);
  }

  private Training updateTrainingFields(TrainingDto dto, Training training) {
    return training
        .setName(dto.name)
        .setDescription(dto.description)
        .setProgrammingLanguage(dto.language)
        .setTeacher(new Teacher(dto.teacherId));
  }

  public void updateTraining(TrainingDto dto) {
    if (trainingRepo.countByNameAndIdNot(dto.name, dto.id) != 0) {
      throw new IllegalArgumentException("Another training with that name already exists");
    }
    Training existing = trainingRepo.findById(dto.id).orElseThrow();
    Training updatedTraining = updateTrainingFields(dto, existing);
    if (!dto.startDate.equals(updatedTraining.getStartDate())) {
      // TODO check date not in the past
      emailSender.sendScheduleChangedEmail(updatedTraining.getTeacher(), updatedTraining.getName(), dto.startDate);
      updatedTraining.setStartDate(dto.startDate);
    }
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

