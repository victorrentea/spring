package victor.training.spring.web.service;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import victor.training.spring.web.MyException;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TrainingSearchRepo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static victor.training.spring.web.MyException.ErrorCode.DUPLICATE_TRAINING_NAME;


// TODO all the methods of the TrainingService need to be logged (params, return)

@Slf4j
@Aspect // custom aspect
@Order(20)
@Component
class MyLoggingAspect {

//    @Around("execution(* victor.training..TrainingService.*(..) )") // 10s way
//    @Around("execution(* victor.training.spring.web.service..*(..) )") // 10s way
    @Around("@within(victor.training.spring.web.service.Logged) ")
//            "and ! @annotation(victor.training.spring.web.service.NotLogged)") // 10s way
    public Object method(ProceedingJoinPoint pjp) throws Throwable {
        log.info("calling " + pjp.getSignature().getName() + " with args " + Arrays.toString(pjp.getArgs()));
        Object result = pjp.proceed();
        log.info("returned " + result);
        return result;
    }
}
@Retention(RetentionPolicy.RUNTIME)
@interface Logged {
    }




@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
@Logged
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
    @Cacheable("training-by-id")
    public TrainingDto getTrainingById(Long id) {
//        new RuntimeException().printStackTrace();
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
//    @PreAuthorized
//    @Transactional
    @CacheEvict(value = "training-by-id", key = "#id2")
    public void updateTraining(Long id2, TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null &&  !trainingRepo.getByName(dto.name).getId().equals(id2)) {
//            throw new DuplicatedTrainingNameException(); // never
            throw new MyException(DUPLICATE_TRAINING_NAME);
//            throw new IllegalArgumentException("Another training with that name already exists");
        }
//            throw new IllegalArgumentException("another exception");
        Training training = trainingRepo.findById(id2).orElseThrow();
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

//    @Retryable
    public void createTraining(@Validated TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null) {
            throw new MyException(DUPLICATE_TRAINING_NAME);
//            throw new IllegalArgumentException("Another training with that name already exists");
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

