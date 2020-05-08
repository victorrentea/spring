package com.example.demo.service;

import com.example.demo.dto.TeacherDto;
import com.example.demo.entity.Teacher;
import com.example.demo.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    public List<TeacherDto> getAllTeachers() {
        return teacherRepo.findAll().stream().map(TeacherDto::new).collect(Collectors.toList());
    }

}
