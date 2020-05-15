package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.dto.TrainingDto;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.Training;
import com.example.demo.repo.TrainingRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
// Fix3: [solutia buna] Fiecare test sa ruleze in propria tranzactie,
// pe care spring are grija sa o ROLLBACK-uie la final automat.
@Transactional
public class TrainingServiceTest {
    @Autowired
    private TrainingService service;
    @Autowired
    private TrainingRepo trainingRepo;

    @Before
    public void checkNoTrainingsInDb() {
        // Fix2: sterg baza inainte mea. Prost ca poate scapa gunoi. Prost ca tre sa le stergi in ordinea FKurilor
//        trainingRepo.deleteAll();


        long n = trainingRepo.count();
        if (n != 0) {
            throw new IllegalStateException("N-ar trebui sa fie date in baza. Am gasit " + n + " traininguri");
        }
    }

    @Test
    // FIX1: arunci tot springu la gunoi dupa COMMITul tau. Problema: mai pierzi 5-15 sec sa reporneasca springul
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getAll() {
        Training training = new Training();
        training.setStartDate(LocalDate.now());
        training.setTeacher(new Teacher("Tavi"));
        trainingRepo.save(training);
        List<TrainingDto> dtos = service.getAllTrainings();
        assertEquals(1, dtos.size());
        TrainingDto dto = dtos.get(0);
        assertEquals("2020-05-15", dto.startDate);
    }

    @Test
    public void getAll2() {
        Training training = new Training();
        training.setStartDate(LocalDate.now());
        training.setTeacher(new Teacher("Tavi"));
        trainingRepo.save(training);
        List<TrainingDto> dtos = service.getAllTrainings();
        assertEquals(1, dtos.size());
        TrainingDto dto = dtos.get(0);
        assertEquals("2020-05-15", dto.startDate);
    }
}
