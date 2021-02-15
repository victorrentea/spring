package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final AnotherClass other;

    @Transactional
    public void transactionOne() {
        repo.save(new Message("jpa")); // are @Transactional pe ea
        System.out.println(repo.findByMessageLike("%p%"));
        System.out.println(repo.finduMeu("%P%"));
//        String bum = StringUtils.repeat("a", 256);
//        repo.save(new Message(bum));
        System.out.println("Gata, ies din metoda. Ma duc la proxy...");
    }

    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(1L).get();

        message.setMessage("Nou mesaj");

        Message m2 = other.surprizaCaIauDinAcelasiCache();

        // TODO Repo API
        // TODO @NonNullApi
        System.out.println(message == m2);
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    public Message surprizaCaIauDinAcelasiCache() {

       return repo.findById(1L).get();
    }


    @Transactional
    public void citireMasiva() {

        Stream<Message> si iteram pe el.
                                         Hibernate-ul dragutzul, tinea cate o copie din fiecare entitate incarcata,
            ca poate o schimb.

            Iar eu iteram pe 10.000.000 de entiti.

        try {
            Sa bem
            OOM
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}