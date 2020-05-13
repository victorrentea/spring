package com.example.demo.service;

import com.example.demo.entity.Teacher;
import com.example.demo.repo.TeacherRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Alta {
    private final TeacherRepo teacherRepo;
    private final JdbcTemplate jdbcTemplate;

   public Alta(TeacherRepo teacherRepo, JdbcTemplate jdbcTemplate) {
        this.teacherRepo = teacherRepo;
       this.jdbcTemplate = jdbcTemplate;
   }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void aruncate() {

        teacherRepo.save(new Teacher("Tavi"));
        throw new IllegalArgumentException();
    }

    @Transactional
    public void inseraSiTeacher() {
        teacherRepo.save(new Teacher("Tavi"));
        jdbcTemplate.update("INSERT INTO TEACHER(id, name) VALUES (99, 'profu')");
    }
}
