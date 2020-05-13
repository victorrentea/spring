package com.example.demo.repo;

import com.example.demo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
//
//@Repository
//public class TeacherRepo {
//    private final JdbcTemplate jdbcTemplate;
//
//    public TeacherRepo(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public Teacher getOne(Long teacherId) {
//        return jdbcTemplate.queryForObject("SELECT id, name FROM TEACHER WHERE ID=?", mapTeacher(), teacherId);
//    }
//
//    private RowMapper<Teacher> mapTeacher() {
//        return (rs, rowNum) -> {
//            Teacher teacher = new Teacher();
//            teacher.setId(rs.getLong("id"));
//            teacher.setName(rs.getString("name"));
//            return teacher;
//        };
//    }
//
//    public Collection<Teacher> findAll() {
//        return jdbcTemplate.query("SELECT id, name FROM TEACHER", mapTeacher());
//    }
//
//}