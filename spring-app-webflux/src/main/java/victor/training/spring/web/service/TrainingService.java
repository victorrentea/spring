//package victor.training.spring.web.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;
//import victor.training.spring.web.controller.dto.TrainingDto;
//import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
//import victor.training.spring.web.entity.Teacher;
//import victor.training.spring.web.entity.Training;
//import victor.training.spring.web.entity.TrainingId;
//import victor.training.spring.web.repo.ProgrammingLanguageRepo;
//import victor.training.spring.web.repo.TeacherRepo;
//import victor.training.spring.web.repo.TrainingRepo;
//
//import javax.validation.Valid;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//@Validated
//public class TrainingService {
//    private final TrainingRepo trainingRepo;
//    private final ProgrammingLanguageRepo languageRepo;
//    private final TeacherRepo teacherRepo;
//    private final EmailSender emailSender;
//    private final TeacherBioClient teacherBioClient;
//
//    public List<TrainingDto> getAllTrainings() {
//        List<TrainingDto> dtos = new ArrayList<>();
//
//        for (Training training : trainingRepo.findAll()) {
//            dtos.add(mapToDto(training));
//        }
//        return dtos;
//    }
//
//    public TrainingDto getTrainingById(TrainingId id) {
//        TrainingDto dto = mapToDto(trainingRepo.findById(id.id()).orElseThrow());
//        try {
//            dto.teacherBio = teacherBioClient.retrieveBiographyForTeacher(dto.teacherId);
//        } catch (Exception e) {
//            log.error("Error retrieving bio", e);
//            dto.teacherBio = "<ERROR RETRIEVING TEACHER BIO (see logs)>";
//        }
//        return dto;
//    }
//
//    // TODO Test this!
//    public void updateTraining(Long id, TrainingDto dto) throws ParseException {
//        if (trainingRepo.getByName(dto.name) != null && !trainingRepo.getByName(dto.name).getId().equals(id)) {
//            throw new IllegalArgumentException("Another training with that name already exists");
//        }
//        Training training = trainingRepo.findById(id).orElseThrow();
//        training.setName(dto.name);
//        training.setDescription(dto.description);
//        // TODO implement date not in the past using i18n error message
//        training.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        training.setProgrammingLanguageId(dto.languageId);
//        training.setTeacherId(dto.teacherId);
//    }
//
//    public void deleteById(Long id) {
//        trainingRepo.deleteById(id);
//    }
//
//    public void createTraining(@Valid TrainingDto dto) throws ParseException {
//        new RuntimeException().printStackTrace();
//        if (trainingRepo.getByName(dto.name) != null) {
//            throw new IllegalArgumentException("Another training with that name already exists");
//        }
//        trainingRepo.save(mapToEntity(dto));
//    }
//
//    private TrainingDto mapToDto(Training training) {
//        TrainingDto dto = new TrainingDto();
//        dto.id = training.getId();
//        dto.name = training.getName();
//        dto.description = training.getDescription();
//        dto.startDate = new SimpleDateFormat("dd-MM-yyyy").format(training.getStartDate());
//        dto.teacherId = training.getTeacherId();
//        dto.languageId = training.getProgrammingLanguageId();
//        dto.teacherName = "TODO" + training.getTeacherId();
//        return dto;
//    }
//
//    private Training mapToEntity(TrainingDto dto) throws ParseException {
//        Training newEntity = new Training();
//        newEntity.setName(dto.name);
//        newEntity.setDescription(dto.description);
//        newEntity.setProgrammingLanguageId(dto.languageId);
//        newEntity.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        newEntity.setTeacherId(dto.teacherId);
//        return newEntity;
//    }
//
//    public List<TrainingDto> search(TrainingSearchCriteria criteria) {
//        log.debug("Search by " + criteria);
//        throw new IllegalArgumentException("Not implemnented");
//    }
//}
//
