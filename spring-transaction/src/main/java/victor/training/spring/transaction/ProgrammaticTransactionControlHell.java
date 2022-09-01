package victor.training.spring.transaction;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ProgrammaticTransactionControlHell {
   DataSource dataSource;
   @Transactional
   public void oneTopLevel() throws SQLException {
      Connection conn = dataSource.getConnection();
      conn.setAutoCommit(false); // typically setup on Conn Pool

      try {
         // programmatic Hibernate tx mgmt : pass the session arround.
//         Session s;
//         s.beginTransaction();


         // programmatic JPA tx mgmt : pass the em arround.
//         EntityManager em;
//         em.getTransaction().begin();
//         em.getTransaction().com

         a(conn);
      } finally {
         conn.rollback();
         conn.close();
      }
   }

   private void a(Connection conn) throws SQLException {
      conn.createStatement().executeQuery("INSERT ..."); // would this be commited?
      b(conn);
   }

   private void b(Connection conn) throws SQLException {
      if (Math.random() < .5) {
         conn.rollback();
      }
      c(conn);
      conn.createStatement().executeQuery("INSERT ..."); // would this be commited?
   }

   private void c(Connection conn) throws SQLException {
      if (Math.random() < .5) {
         conn.commit();
      }
   }
}
