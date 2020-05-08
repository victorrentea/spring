package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;

import java.util.*;

public interface TrainingRepo extends JpaRepository<Training, Long> {
    Training getByName(String name);
}
//
//public class TrainingRepo {
//    private long idSequence;
//    private final Map<Long, Training> data = new HashMap<>();
//
//    public synchronized void save(Training training) {
//        training.setId(idSequence++);
//        data.put(training.getId(), training);
//    }
//
//    public Training getOne(Long teacherId) {
//        return data.get(teacherId);
//    }
//
//    public Collection<Training> findAll() {
//        return data.values();
//    }
//
//    public void deleteById(Long id) {
//        data.remove(id);
//    }
//
//    public Training getByName(String name) {
//        return data.values().stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
//    }
//
//    public Optional<Training> findById(Long id) {
//        return Optional.ofNullable(data.get(id));
//    }
//}