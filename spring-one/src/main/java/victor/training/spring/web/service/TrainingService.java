package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.MyException;
import victor.training.spring.web.MyException.ErrorCode;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TeacherRepo;

import javax.servlet.*;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Aspect // 1-2 per applicatie, maxim
@Component
class LoggingInterceptor {

//    @Around("execution(* victor.training.spring.web..*.*(..))")
//    @Around("@within(victor.training.spring.web.service.Logged)") // @ pe clasa
    @Around("@annotation(victor.training.spring.web.service.Logged)") // @ pe metoda
    public Object logCall(ProceedingJoinPoint pjp) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Calling {} ({})", pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
        }  else {
            log.info("Calling {}", pjp.getSignature().getName());
        }

        Object result = pjp.proceed();
        log.info("Result: " + result);
        return result;

        // WARNING: sa nu te duca gandul sa faci un fel de custom execution profiler, sa vezi pe unde se pierde timp.
        // Pentru asta ai deja in JVM Java Flight Recorder -- face fix asta
    }
}
@Retention(RetentionPolicy.RUNTIME)
@interface Logged {}


@Slf4j
@Service
//@Logged
@Transactional
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private EmailSender emailSender;

    @Logged
    public List<TrainingDto> getAllTrainings() {
//        new RuntimeException().printStackTrace();
        List<TrainingDto> dtos = new ArrayList<>();
        for (Training training : trainingRepo.findAll()) {
            dtos.add(mapToDto(training));
        }
        return dtos;
    }

    public Optional<TrainingDto> getTrainingById(Long id) {

        Optional<Training> trainingEntityOpt = trainingRepo.findById(id);
        return trainingEntityOpt.map(this::mapToDto);
    }

    // TODO Test this!
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {

        if (trainingRepo.getByName(dto.name) != null &&  !trainingRepo.getByName(dto.name).getId().equals(id)) {
            throw new MyException(ErrorCode.DUPLICATED_TRAINING_NAME, dto.name);
        }
        Training training = trainingRepo.findById(id).get();
        training.setName(dto.name);
        training.setDescription(dto.description);
        // TODO implement date not in the past. i18n
        Date newDate = parseStartDate(dto);
        if (!newDate.equals(training.getStartDate())) {
            emailSender.sendScheduleChangedEmail(training.getTeacher(), training.getName(), newDate);
        }
        training.setStartDate(newDate);
        training.setTeacher(teacherRepo.getOne(dto.teacherId));
    }

    private Date parseStartDate(TrainingDto dto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Date date = format.parse(dto.startDate);
        String stringAgain = format.format(date);
        if (!dto.startDate.equals(stringAgain)) {
            throw new IllegalArgumentException("Invalid Date : " + dto.startDate);
        }
        return date;
    }
//    @Secured("ADMIN")
//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('training.edit')")
    public void deleteById(Long id) {
        trainingRepo.deleteById(id);
    }

    public void createTraining(TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.name) != null) {
            throw new MyException(ErrorCode.DUPLICATED_TRAINING_NAME, dto.name);
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
        dto.teacherName = training.getTeacher().getName();
        return dto ;
    }

    private Training mapToEntity(TrainingDto dto) throws ParseException {
        Training newEntity = new Training();
        newEntity.setName(dto.name);
        newEntity.setDescription(dto.description);
        newEntity.setStartDate(parseStartDate(dto));
        newEntity.setTeacher(teacherRepo.getOne(dto.teacherId));
        return newEntity;
    }
}
