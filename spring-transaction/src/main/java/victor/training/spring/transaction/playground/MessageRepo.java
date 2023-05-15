package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Modifying
    @Query(value = "insert into MESSAGE(id, message) values ( 100,?1 )", nativeQuery = true)
    void nativeQueryInSpringDataJPA(String name);
}
