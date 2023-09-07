package victor.training.spring.transaction.playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

//@Repository// not needed
public class MessageRepoCustomImpl implements MessageRepoCustom{
  @Autowired
  EntityManager entityManager;
  @Override
  public void manuallyImplemented() {
//    entityManager.createQuery("jqpl manually ")
  }
}
