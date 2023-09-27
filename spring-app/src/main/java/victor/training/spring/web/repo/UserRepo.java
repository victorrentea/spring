package victor.training.spring.web.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import victor.training.spring.web.entity.User;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.ErrorManager;

//@Repository nu e necesar
// spring genereaza o implem la runtime dinamica
// Spring DATA care sta peste JPA
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.managedTeacherIds WHERE u.username = ?1")
    Optional<User> findByUsernameForLogin(String username);
}

@Repository // e necesara doar
class UserDAO {
    @Autowired
    private EntityManager entityManager;

    public void method() { // DIRECT JPA
//        entityManager.find()
    }
}


@Component
class AstaMerge {
    @Autowired
    private UserRepo userRepo;
}