package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import victor.training.spring.web.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.managedTeacherIds " +
           "WHERE u.username = ?1")
    Optional<User> getForLogin(String username);

}
