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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TrainingServiceTest {
    @Autowired
    private TrainingService service;
    @Autowired
    private TrainingRepo trainingRepo;

    @Before
    public void checkNoTrainingsInDb() {
        if (trainingRepo.count() != 0) {
            throw new IllegalStateException("N-ar trebui sa fie date in baza");
        }
    }

    @Test
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
}
