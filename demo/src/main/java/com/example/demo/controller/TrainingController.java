package com.example.demo.controller;

import com.example.demo.dto.TrainingDto;
import com.example.demo.service.TrainingService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("rest/training")
public class TrainingController {
    private final TrainingService service;

    public TrainingController(TrainingService service) {
        this.service = service;
    }

    @GetMapping
    public List<TrainingDto> findAll() {
        return service.getAllTrainings();
    }

    @PostMapping
    public void create(@RequestBody TrainingDto dto) {
        service.createTraining(dto);
    }

    @GetMapping("{trainingId}")
    public TrainingDto getById(@PathVariable long trainingId) {
        return service.getTrainingById(trainingId);
    }

    @PutMapping("{id}")
    public void update(@PathVariable long id, @RequestBody TrainingDto dto) throws ParseException {
        service.updateTraining(id, dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        service.deleteTrainingById(id);
    }
}
