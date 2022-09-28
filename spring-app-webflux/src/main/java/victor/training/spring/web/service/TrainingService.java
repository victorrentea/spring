package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.entity.TrainingId;
import victor.training.spring.web.repo.TrainingRepo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static reactor.core.publisher.Mono.just;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Validated
public class TrainingService {
    private final TrainingRepo trainingRepo;
    private final TeacherBioClient teacherBioClient;

    public Flux<TrainingDto> getAllTrainings() {
        return trainingRepo.findAll().map(this::mapToDto);
    }

    public Mono<TrainingDto> getTrainingById(TrainingId id) {
        return trainingRepo.findById(id.id())
                .map(this::mapToDto)
                .flatMap(dto -> teacherBioClient.retrieveBiographyForTeacher(dto.teacherId)
                        .onErrorReturn("<ERROR RETRIEVING TEACHER BIO (see logs)>")
                        .map(bio -> {
                            dto.teacherBio = bio;
                            return dto;
                        })
                );
    }

    // TODO Test this!
    public Mono<Training> updateTraining(Long id, TrainingDto dto) {
        return trainingRepo.getByName(dto.name)
                .flatMap(existing -> !existing.getId().equals(id) ? Mono.error(new IllegalArgumentException("Another training with that name already exists")) :
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

    public Mono<Void> createTraining(@Valid TrainingDto dto) {
        return trainingRepo.getByName(dto.name)
                .flatMap(t -> Mono.error(new IllegalArgumentException("Another training with that name already exists")))
                .switchIfEmpty(trainingRepo.save(mapToEntity(dto)))
                .then();
    }

    private TrainingDto mapToDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.id = training.getId();
        dto.name = training.getName();
        dto.description = training.getDescription();
        dto.startDate = training.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        dto.teacherId = training.getTeacherId();
        dto.languageId = training.getProgrammingLanguageId();
        dto.teacherName = "TODO" + training.getTeacherId();
        return dto;
//        return teacherRepo.findById(training.getTeacherId())
//                .map(t -> {
//                    dto.teacherName = t.getName();
//                    return dto;
//                });
    }

    @SneakyThrows
    private Training mapToEntity(TrainingDto dto) {
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

