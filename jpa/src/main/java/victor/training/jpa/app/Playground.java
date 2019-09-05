package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;

import javax.persistence.EntityManager;
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


    }

//    @NonNull
    public Integer altaMetoda() {
        return null;
    }

}


interface SomeRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByName(String name);
    Teacher getByNameAndGrade(String name, Grade grade);
    Teacher getByDetailsCv(String cv);

    @Query("FROM Teacher t WHERE t.name=?1 and t.grade=?2")
    Teacher findForGrant(String name, Grade grade);

}
