package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT COUNT(m) FROM Message m") // JPQL
    void myCount();

    @Query(value = "SELECT COUNT(*) FROM MESSAGE",nativeQuery = true) // JPQL
    void myCountNative();

    Optional<Message> getByMessageLikeIgnoreCase(String a);


}
