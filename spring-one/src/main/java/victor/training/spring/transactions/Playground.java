package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class Playground {
    @Autowired
    private final AnotherClass other;

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public void transactionOne() throws Exception {
        getSession().save(new Message("jpa"));
        getSession().save(new Message("jpa"));
        getSession().save(new Message("jpa"));
        getSession().save(new Message("jpa"));
//        getSession().save(new Message("asdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajd"));
        throw new Exception();
    }
    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;
    
    public void method() {

    }
}