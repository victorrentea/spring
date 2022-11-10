package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

// spring data repo implementat DINAMIC de spring la startup§
public interface MessageRepo extends JpaRepository<Message, Long> {
    @Modifying
    @Query(value = "insert into MESSAGE(id, message) values ( 100,'ALO' )", nativeQuery = true)
    void queryNativ();

    // welcome to future -------- JPA: ...

    // cu adnotare
    @Query("SELECT m FROM Message m WHERE m.message = ?1")
    Message finduMeu(String mesajDeCautat);

    Message findByMessage(String mesajDeCautat);

    @Query(value = "UPDATE Message m SET m.message = ?1 WHERE m.id = ?2") // ==> 1 network round trip
    void updateMessageById(String mesajNou, Long id);

    // evitati👆: preferati sa faceti. <== pt design
    // -- dar nu e mai multa retea?
    // -- ba da, doar ca modificarile de date sunt locurile critice unde un bug doare cel mai mult
        // => vreau sa modific date IN JAVA (+Unit Teste, + entitati destepte, reguli de busi)
    // e=repo.find(id) ==> 1 SELECT
    // si apoi
    // e.setMessage(mesajNou) ==> 1 UPDATE
}
