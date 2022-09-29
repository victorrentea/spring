package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static reactor.core.publisher.Mono.just;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Validated
@Service
public class TrainingService {
    private final TrainingRepo trainingRepo;
    private final ProgrammingLanguageRepo languageRepo;
    private final TeacherRepo teacherRepo;
    private final EmailSender emailSender;
    private final TeacherBioClient teacherBioClient;

    public Flux<TrainingDto> getAllTrainings() {
        return trainingRepo.findAll().map(training -> mapToDto(training, null));
    }

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
    public Mono<TrainingDto> getTrainingById(TrainingId trainingId) {
        return trainingRepo.findById(trainingId.id())
                .zipWhen(t -> teacherBioClient.retrieveBio(t.getTeacherId())
                        .onErrorReturn("<ERROR RETRIEVING TEACHER BIO (see logs)>"),
                    this::mapToDto)
                ;
    }

    // TODO Test this!
    public Mono<Training> updateTraining(Long id, TrainingDto dto) {
        return trainingRepo.getByName(dto.name)
                .flatMap(existing -> !existing.getId().equals(id) ?
                        Mono.error(new IllegalArgumentException("Another training with that name already exists"))
                        :
                        just(dto))
                .switchIfEmpty(just(dto))
                .flatMap(d -> trainingRepo.findById(id).map(training -> changeEntity(training, d)))
                .flatMap(e -> trainingRepo.save(e));
    }

    private Training changeEntity(Training training, TrainingDto dto) {
        training.setName(dto.name);
        training.setDescription(dto.description);
        training.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        training.setProgrammingLanguageId(dto.languageId);
        training.setTeacherId(dto.teacherId);
        return training;
    }


    public Mono<Void> deleteById(Long id) {
        return trainingRepo.deleteById(id);
    }


    public Mono<Void> createTraining(@Valid TrainingDto dto) throws ParseException {
        return trainingRepo.getByName(dto.name)
                .flatMap(whatever -> Mono.error(new IllegalArgumentException("Another training with that name already exists")))
                .switchIfEmpty(trainingRepo.save(mapToEntity(dto)))
                .then();

    }

    private TrainingDto mapToDto(Training training, String bio) {
        TrainingDto dto = new TrainingDto(); // todo immutables.
        dto.id = training.getId();
        dto.name = training.getName();
        dto.description = training.getDescription();
        dto.startDate = training.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        dto.teacherId = training.getTeacherId();
        dto.languageId = training.getProgrammingLanguageId();
        dto.teacherName = "TODO" + training.getTeacherId();
        dto.teacherBio = bio;
        return dto;
    }

    private Training mapToEntity(TrainingDto dto) throws ParseException {
        Training newEntity = new Training();
        newEntity.setName(dto.name);
        newEntity.setDescription(dto.description);
        newEntity.setProgrammingLanguageId(dto.languageId);
        newEntity.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        newEntity.setTeacherId(dto.teacherId);
        return newEntity;
    }

    public List<TrainingDto> search(TrainingSearchCriteria criteria) {
        log.debug("Search by " + criteria);
        throw new IllegalArgumentException("Not implemnented");
    }
}

