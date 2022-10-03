package victor.training.spring.web.service;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TrainingSearchRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class TrainingService {
    private final TrainingRepo trainingRepo;
    private final TrainingSearchRepo trainingSearchRepo;
    private final ProgrammingLanguageRepo languageRepo;
    private final TeacherRepo teacherRepo;
    private final EmailSender emailSender;
    private final TeacherBioClient teacherBioClient;

    public List<TrainingDto> getAllTrainings() {
        List<TrainingDto> dtos = new ArrayList<>();
        for (Training training : trainingRepo.findAll()) {
            dtos.add(mapToDto(training));
        }
        return dtos;
    }

    public TrainingDto getTrainingById(Long id) {
        TrainingDto dto = mapToDto(trainingRepo.findById(id).orElseThrow());
        try {
            dto.teacherBio = teacherBioClient.retrieveBiographyForTeacher(dto.teacherId);
        } catch (Exception e) {
            log.error("Error retrieving bio", e);
            dto.teacherBio = "<ERROR RETRIEVING TEACHER BIO (see logs)>";
        }
        return dto;
    }

    // TODO Test this!
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null &&  !trainingRepo.getByName(dto.name).getId().equals(id)) {
            throw new IllegalArgumentException("Another training with that name already exists");
        }
        Training training = trainingRepo.findById(id).orElseThrow();
        training.setName(dto.name);
        training.setDescription(dto.description);
        // TODO implement date not in the past using i18n error message
        Date newDate = parseStartDate(dto);
        if (!newDate.equals(training.getStartDate())) {
            emailSender.sendScheduleChangedEmail(training.getTeacher(), training.getName(), newDate);
        }
        training.setStartDate(newDate);
        training.setProgrammingLanguage(languageRepo.getById(dto.languageId));
        training.setTeacher(teacherRepo.getById(dto.teacherId));
    }

    private Date parseStartDate(TrainingDto dto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.parse(dto.startDate);
    }

    public void deleteById(Long id) {
        trainingRepo.deleteById(id);
    }

    @Timed("create-training")
    public void createTraining(TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null) {
            throw new IllegalArgumentException("Another training with that name already exists");
        }
        trainingRepo.save(mapToEntity(dto));
    }

    private TrainingDto mapToDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.id = training.getId();
        dto.name = training.getName();
        dto.description = training.getDescription();
        dto.startDate = new SimpleDateFormat("dd-MM-yyyy").format(training.getStartDate());
        dto.teacherId = training.getTeacher().getId();
        dto.languageId = training.getProgrammingLanguage().getId();
        dto.teacherName = training.getTeacher().getName();
        return dto ;
    }

    private Training mapToEntity(TrainingDto dto) throws ParseException {
        Training newEntity = new Training();
        newEntity.setName(dto.name);
        newEntity.setDescription(dto.description);
        newEntity.setProgrammingLanguage(languageRepo.getById(dto.languageId));
        newEntity.setStartDate(parseStartDate(dto));
        newEntity.setTeacher(teacherRepo.getById(dto.teacherId));
        return newEntity;
    }

    public List<TrainingDto> search(TrainingSearchCriteria criteria) {
        log.debug("Search by " + criteria);
        return trainingSearchRepo.search(criteria).stream()
            .map(this::mapToDto)
            .collect(toList());
    }
}

