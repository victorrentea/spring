package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.security.SecurityUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private TrainingSecurity trainingSecurity;

    public List<TrainingDto> getAllTrainings() {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Long> allowedTeacherIds = securityUser.getManagedTeacherIds();

        List<TrainingDto> dtos = new ArrayList<>();
        for (Training training : trainingRepo.findAll()) {
            TrainingDto dto = mapToDto(training);
            dto.canDelete = allowedTeacherIds.contains(training.getTeacher().getId());
            dtos.add(dto);
        }
        return dtos;
    }

    public TrainingDto getTrainingById(Long id) {
        return mapToDto(trainingRepo.findById(id).get());
    }

    // TODO Test this!
    @PreAuthorize("@trainingSecurity.canUpdateTraining(#id)")
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {
//        trainingSecurity.checkCanUpdateTraining(id);
        if (trainingRepo.getByName(dto.name) != null &&  !trainingRepo.getByName(dto.name).getId().equals(id)) {
            throw new IllegalArgumentException("Another training with that name already exists");
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
        return format.parse(dto.startDate);
    }

//    @PreAuthorize("hasAnyRole('ADMIN')")

    public void deleteById(Long id) {
//        trainingSecurity.checkCanUpdateTraining(id);
        trainingRepo.deleteById(id);
    }

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

    public List<TrainingDto> search(TrainingSearchCriteria criteria) {
        log.debug("Search by " + criteria);
        return Collections.emptyList(); // TODO
    }
}

