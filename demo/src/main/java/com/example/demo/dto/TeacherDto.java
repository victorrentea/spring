package com.example.demo.dto;

import com.example.demo.entity.Teacher;

// JSON
public class TeacherDto {
    public Long id;
    public String name;

    public TeacherDto(Teacher entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
