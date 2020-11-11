package victor.training.spring.web.domain;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CopilCuminte {
   @Id
   private Long id;
   @Setter
   @ManyToOne
   private EntitateCuPretentii parinte;

}
