package victor.training.spring.transactions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoanPattern {
   EntityManagerRunner runner;

   public final List<Entry> readAll() {

      List<Entry> results = new ArrayList<>(runner.run(em ->
          em.createQuery("from EntryImpl order by alias", EntryImpl.class)
              .getResultList()));

      final List<Entry> entries = new ArrayList<>(results.size());
      for (final Entry crtEntry : results) {
         entries.add(crtEntry);
      }
      return entries;
   }


}

class EntityManagerRunner {
   private final EntityManagerFactory entityManagerFactory;

   EntityManagerRunner(EntityManagerFactory entityManagerFactory) {
      this.entityManagerFactory = entityManagerFactory;
   }

   public <T> T run(Function<EntityManager, T> task) {
      EntityManager em = entityManagerFactory.createEntityManager();
      try {
         return task.apply(em);
      } finally {
         em.close();
      }
   }
}



interface Entry {

}

class EntryImpl implements Entry{

}

