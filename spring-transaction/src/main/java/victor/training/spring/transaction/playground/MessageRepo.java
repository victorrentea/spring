package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MessageRepo extends JpaRepository<Message, Long> {
  @Query(value = """
      insert into MESSAGE(id, message)
      values (100,'SQL' )
      """ // SQL
      ,nativeQuery = true)
  @Modifying
  @Transactional // cand scrii prin JPA in DB ai nevoie de TX in jurul tau
  void queryNativ();

  //ne-nativ inseamna QL de JPA (JPQL/HQL)
//  @Query(value = "...")
//  void queryJPA();
}
