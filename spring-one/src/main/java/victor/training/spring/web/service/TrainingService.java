package victor.training.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.MyException;
import victor.training.spring.web.MyException.ErrorCode;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
//@Validated
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private EmailSender emailSender;

    public List<TrainingDto> getAllTrainings() {
        List<TrainingDto> dtos = new ArrayList<>();
        for (Training training : trainingRepo.findAll()) {
            dtos.add(mapToDto(training));
        }
        return dtos;
    }

    public TrainingDto getTrainingById(Long id) {
        return mapToDto(trainingRepo.findById(id).get());
    }

    // TODO Test this!
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {
        if (trainingRepo.getByName(dto.getName()) .isPresent() &&
            !trainingRepo.getByName(dto.getName()).get().getId().equals(id)) {
            throw new MyException(ErrorCode.DUPLICATE_COURSE_NAME);
        }
        Training training = trainingRepo.findById(id).get();
        training.setName(dto.getName());
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

    EntityManager em;
    public void deleteById(Long id) {
        trainingRepo.findById(id);
        em.createQuery("SELECT t FROM Training t WHERE t.name = :name")
            .setParameter("name","JPA")
            .executeUpdate();

        trainingRepo.deleteById(id);
    }


    public void createTraining(@Valid TrainingDto dto) throws ParseException {
        System.out.println("Oare aici");
        new RuntimeException().printStackTrace();
        if (trainingRepo.getByName(dto.getName()).isPresent()) {
            throw new MyException(ErrorCode.DUPLICATE_COURSE_NAME);
        }
        trainingRepo.save(mapToEntity(dto));
    }

    private TrainingDto mapToDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.id = training.getId();
        dto.setName(training.getName());
        dto.description = training.getDescription();
        dto.startDate = new SimpleDateFormat("dd-MM-yyyy").format(training.getStartDate());
        dto.teacherId = training.getTeacher().getId();
        dto.teacherName = training.getTeacher().getName();
        return dto ;
    }

    private Training mapToEntity(TrainingDto dto) throws ParseException {
        Training newEntity = new Training();
        newEntity.setName(dto.getName());
        newEntity.setDescription(dto.description);
        newEntity.setStartDate(parseStartDate(dto));
        newEntity.setTeacher(teacherRepo.getOne(dto.teacherId));
        return newEntity;
    }

    public List<TrainingDto> search(TrainingSearchCriteria criteria) {
        return trainingRepo.search(criteria).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList()); // TODO
    }

    @Transactional
    public void updateTrainingStartDate(Long id, Date startDate) {
        Training training = trainingRepo.findById(id).get();
        training.setStartDate(startDate);
    }
}
