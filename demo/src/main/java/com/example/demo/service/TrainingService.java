package com.example.demo.service;

import com.example.demo.DemoException;
import com.example.demo.dto.TrainingDto;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.Training;
import com.example.demo.repo.TeacherRepo;
import com.example.demo.repo.TrainingRepo;
import com.example.demo.security.DemoPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private TrainingMapper trainingMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Cacheable("trainings")
    public List<TrainingDto> getAllTrainings() {
        return trainingRepo.findAll().stream().map(training -> new TrainingDto(training)).collect(toList());
    }

    public TrainingDto getTrainingById(Long id) {

        return new TrainingDto(trainingRepo.findById(id).get());
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasRole('CAN_DELETE_TRAINING')") <-- permission
    @CacheEvict(value = "trainings", allEntries = true)
    // TODO Test this!
    public void deleteTrainingById(Long id) {
        trainingRepo.deleteById(id);
    }

    @CacheEvict(value = "trainings", allEntries = true)
    public void createTraining(TrainingDto dto) {
        if (trainingRepo.getByName(dto.name).isPresent()) {
            throw new IllegalArgumentException("Another course with that name already exists");
        }
        Training newEntity = trainingMapper.mapToEntity(dto);
        trainingRepo.save(newEntity);
    }

    @CacheEvict(value = "trainings", allEntries = true)

    // Exercitiu pentru cititor: implementeaza ACL-urile intr-un alt bean.
//    @PreAuthorize("#numeleUnuiBean.verificaCaAreVoieUserulCurentPeTraningul(#id)")
    @Transactional
    public void updateTraining(Long id, TrainingDto dto) throws ParseException {


        System.out.println("De vazut: " + trainingRepo.getClass());

//        trainingRepo.getByName(dto.name).ifPresent(t -> {
//                if (!t.getId().equals(id)) {
//                throw new DemoException(DemoException.ErrorCode.DUPLICATE_NAME, dto.name);
//            }
//        });

//        if (!trainingRepo.getByName(dto.name).map(Training::getId).orElse(-1L).equals(id)) {
////            throw new DemoException(DemoException.ErrorCode.DUPLICATE_NAME, dto.name);
////        }

        // ---> id: 5, name: JPA
        // DB: id: 5, name: JPA --- asta esti chiar tu

        if (trainingRepo.countOtherWithSameName(dto.name, id) != 0) {
            throw new DemoException(DemoException.ErrorCode.DUPLICATE_NAME, dto.name);
        }

//        if (trainingRepo.countByNameAndIdNot(dto.name, id) != 0) {

        Training training = trainingRepo.findById(id).get();
        training.setName(dto.name);
        training.setDescription(dto.description);
        training.setStartDate(LocalDate.parse(dto.startDate, DateTimeFormatter.ISO_DATE));
        training.setTeacher(teacherRepo.getOne(dto.teacherId));


        DemoPrincipal principal = (DemoPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Long> allowedTeacherIds = principal.getManagedTeacherIds();
        Long thisTrainingTeacherId = training.getTeacher().getId();
        if (!allowedTeacherIds.contains(thisTrainingTeacherId)) {
            throw new IllegalArgumentException("N-ai voie");
        }



        log.debug("IES");
        alta.inseraSiTeacher();
        try {
            alta.aruncate();
            // sau, programatic: ..
            TransactionTemplate txTem = new TransactionTemplate(transactionManager);
            txTem.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            txTem.execute(callback ->
            {
                teacherRepo.save(new Teacher("Tavi"));
                throw new IllegalArgumentException();
            });
        } catch (Exception e) {
            System.out.println("SHAWORMA - posibil career path daca inghiti exceptii in halul asta frecvent");
        }
        // TODO missing repo.update(training)
    }

    @Autowired
    private Alta alta;
}
