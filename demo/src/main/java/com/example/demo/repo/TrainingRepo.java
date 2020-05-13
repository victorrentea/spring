package com.example.demo.repo;

import com.example.demo.entity.Teacher;
import com.example.demo.entity.Training;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

//public interface TrainingRepo extends JpaRepository<Training, Long> {
//    Training getByName(String name);
//}
//
@Repository

public class TrainingRepo {
    private final JdbcTemplate jdbc;
    private long idSequence;
    private final Map<Long, Training> data = new HashMap<>();

    public TrainingRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public synchronized void save(Training training) {
        Long newId = jdbc.queryForObject("SELECT TRAINING_SEQ.nextval from DUAL", Long.class);
        jdbc.update("INSERT INTO TRAINING(id, name, description, start_date, teacher_id)" +
//                " values (:id, :name, :description, :startDate,:teacherId)",
                " values (?, ?, ?, ?, ?)",
                newId,
                training.getName(),
                training.getDescription(),
                Date.valueOf(training.getStartDate().get()),
                training.getTeacher().getId()
                );
        training.setId(newId);
//        data.put(training.getId(), training);
    }

    public Training getOne(Long trainingId) {
        return jdbc.queryForObject(
                "SELECT tr.id, tr.name, tr.description,  tr.start_date, tr.teacher_id, " +
                        "te.name teacher_name " +
                        "FROM TRAINING tr " +
                        " LEFT JOIN TEACHER te ON te.id = tr.teacher_id" +
                        " WHERE ID=?",
                mapTraining(), trainingId);
    }

    private RowMapper<Training> mapTraining() {
        return (rs, rowNum) -> {
            Training training = new Training();
            training.setId(rs.getLong("id"));
            training.setName(rs.getString("name"));
            training.setDescription(rs.getString("description"));
            training.setStartDate(rs.getDate("start_date").toLocalDate());
            training.setTeacher(new Teacher());
            training.getTeacher().setId(rs.getLong("teacher_id"));
            training.getTeacher().setName(rs.getString("teacher_name"));
            return training;
        };
    }

    public Collection<Training> findAll() {
        return jdbc.query("SELECT tr.id, tr.name, tr.description,  tr.start_date, tr.teacher_id, " +
                        "te.name teacher_name " +
                        "FROM TRAINING tr " +
                        " LEFT JOIN TEACHER te ON te.id = tr.teacher_id"
                        ,
                mapTraining());
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM TRAINING WHERE ID = ? ", id);
    }

    public Training getByName(String name) {
        List<Training> list = jdbc.query(
                "SELECT tr.id, tr.name, tr.description,  tr.start_date, tr.teacher_id, " +
                        "te.name teacher_name " +
                        "FROM TRAINING tr " +
                        " LEFT JOIN TEACHER te ON te.id = tr.teacher_id" +
                        " WHERE tr.name=?",
                mapTraining(), name);
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public Optional<Training> findById(Long id) {
        List<Training> list = jdbc.query(
                "SELECT tr.id, tr.name, tr.description,  tr.start_date, tr.teacher_id, " +
                        "te.name teacher_name " +
                        "FROM TRAINING tr " +
                        " LEFT JOIN TEACHER te ON te.id = tr.teacher_id" +
                        " WHERE tr.ID=?",
                mapTraining(), id);
        if (list.size() == 1) {
            return of(list.get(0));
        } else if (list.isEmpty()) {
            return empty();
        } else {
            throw new RuntimeException("Too many rows!");
        }
    }
}