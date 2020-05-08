package com.example.demo.repo;

import com.example.demo.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//public interface TeacherRepo extends JpaRepository<Teacher, Long> {
//}

@Repository
public class TeacherRepo {
    private long idSequence;
    private final Map<Long, Teacher> data = new HashMap<>();

    public synchronized void save(Teacher teacher) {
        teacher.setId(idSequence++);
        data.put(teacher.getId(), teacher);
    }

    public Teacher getOne(Long teacherId) {
        return data.get(teacherId);
    }

    public Collection<Teacher> findAll() {
        return data.values();
    }

    public void deleteById(Long id) {
        data.remove(id);
    }
}