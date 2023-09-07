package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//@Repository// not needed
public interface MessageRepo extends
    JpaRepository<Message, Long>,
    MessageRepoCustom {
//  @Query("")
//  f();
}
