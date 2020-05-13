package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import victor.training.spring.web.domain.Teacher;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
//
//@Repository
//public class TeacherRepo {
//    private long idSequence;
//    private final Map<Long, Teacher> data = new HashMap<>();
//
//    public synchronized void save(Teacher teacher) {
//        teacher.setId(idSequence++);
//        data.put(teacher.getId(), teacher);
//    }
//
//    public Teacher getOne(Long teacherId) {
//        return data.get(teacherId);
//    }
//
//    public Collection<Teacher> findAll() {
//        return data.values();
//    }
//
//    public void deleteById(Long id) {
//        data.remove(id);
//    }
//}