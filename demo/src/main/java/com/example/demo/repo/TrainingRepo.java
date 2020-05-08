package com.example.demo.repo;

import com.example.demo.entity.Training;
import org.springframework.stereotype.Repository;

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
    private long idSequence;
    private final Map<Long, Training> data = new HashMap<>();

    public synchronized void save(Training training) {
        training.setId(idSequence++);
        data.put(training.getId(), training);
    }

    public Training getOne(Long teacherId) {
        return data.get(teacherId);
    }

    public Collection<Training> findAll() {
        return data.values();
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