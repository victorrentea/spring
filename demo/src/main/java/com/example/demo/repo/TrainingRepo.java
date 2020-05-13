package com.example.demo.repo;

import com.example.demo.entity.Teacher;
import com.example.demo.entity.Training;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        training.setId(idSequence++);
        data.put(training.getId(), training);
    }

    public Training getOne(Long teacherId) {
        return jdbc.queryForObject(
                "SELECT tr.id, tr.name, tr.description,  tr.start_date, tr.teacher_id, " +
                        "te.name teacher_name " +
                        "FROM TRAINING tr " +
                        " LEFT JOIN TEACHER te ON te.id = tr.teacher_id" +
                        " WHERE ID=?",
                mapTraining(), teacherId);
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
        data.remove(id);
    }

    public Training getByName(String name) {
        return data.values().stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
    }

    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }
}