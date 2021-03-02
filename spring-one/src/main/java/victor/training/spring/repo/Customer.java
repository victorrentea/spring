package victor.training.spring.repo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//@NamedQueries({
//    @NamedQuery(name="a", query = "SELECT c FROM Customer c WHERE UPPER(c.name) LIKE UPER('%' || ?1 || '%')")
//})
public class Customer {
   @Id
   @GeneratedValue
   private Long id;
   private String name;
   private String phone;
}
