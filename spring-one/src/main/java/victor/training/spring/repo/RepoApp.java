package victor.training.spring.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@SpringBootApplication
public class RepoApp implements CommandLineRunner {
   @Autowired
   CustomerRepo repo;

   @Autowired
   private EntityManager entityManager;

   public static void main(String[] args) {
      SpringApplication.run(RepoApp.class, args);
   }

   @Override
   public void run(String... args) throws Exception {
      Customer john = new Customer().setName("John").setPhone("0800");
      repo.save(john);


      System.out.println(repo.findAll());

      System.out.println("De unde stie? " + repo.findByName("John"));
      System.out.println("De unde stie? " + repo.findByNameLike("%oh%"));
      System.out.println("De unde stie? " + repo.findByNamePart("%oH%"));


      TypedQuery<Customer> select = entityManager.createQuery("SELECT c from Customer c", Customer.class);
      System.out.println(select.getResultList());
//      entityManager.createNamedQuery("a")


   }
}
