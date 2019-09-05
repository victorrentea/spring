package victor.training.jpa.app;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);

    @Autowired
    private EntityManager em;

    @Autowired
    private SomeRepo repo;

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
//        List<Teacher> teachers =
//                em.createQuery("SELECT t FROM Teacher t WHERE t.name = :name", Teacher.class)
//                .setParameter("name", "Victor")
//                .getResultList();
//        System.out.println(teachers);
        System.out.println(repo.findById(1L).get());
        System.out.println(repo.findByName("Victor").get());
        System.out.println(repo.findForGrant("Victor", Grade.ASSISTENT));
        System.out.println(repo.getByDetailsCv("A pimped CV"));
        TeacherSearchCriteria criteria = new TeacherSearchCriteria();
//        List<Teacher> teachers = repo.search(criteria);
        criteria.namePart = "v";

        List<TeacherSearchResult> list = repo.search(criteria);
        System.out.println(list.size());
        System.out.println(list);

        Teacher teacher = new Teacher();
        teacher.setName("Victor");
        Example<Teacher> example = Example.of(teacher);
        System.out.println(repo.findAll(example));

    }

//    @NonNull
    public Integer altaMetoda() {
        return null; // TODO
    }
}

class TeacherSearchCriteria {
    public String namePart;
    public Grade grade;
}


interface SomeRepo extends JpaRepository<Teacher, Long>, SomeRepoCustom {

    Optional<Teacher> findByName(String name);
    Teacher getByNameAndGrade(String name, Grade grade);
    Teacher getByDetailsCv(String cv);

    @Query("FROM Teacher t WHERE t.name=?1 and t.grade=?2")
    Teacher findForGrant(String name, Grade grade);

}
interface SomeRepoCustom {

    List<TeacherSearchResult> search(TeacherSearchCriteria criteria);
}
class TeacherSearchResult {
    public final long id;
    public final String name;
    public final Grade grade;

    public TeacherSearchResult(long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "TeacherSearchResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}

class SomeRepoImpl implements SomeRepoCustom {
    @Autowired
    private EntityManager em;

    @Override
    public List<TeacherSearchResult> search(TeacherSearchCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        String jpql = "SELECT new victor.training.jpa.app.TeacherSearchResult(t.id, t.name, t.grade) FROM Teacher t WHERE 1=1 ";
        if (StringUtils.isNotBlank(criteria.namePart)) {
            jpql += "  AND UPPER(t.name) LIKE '%' || :namePart || '%'  ";
            params.put("namePart", criteria.namePart.toUpperCase());
        }
        TypedQuery<TeacherSearchResult> query = em.createQuery(jpql, TeacherSearchResult.class);
        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
        return query.getResultList();
    }
}
