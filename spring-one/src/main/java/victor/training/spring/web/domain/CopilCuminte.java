package victor.training.spring.web.domain;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CopilCuminte {
   @Setter
   @ManyToOne
   private EntitateCuPretentii parinte;

}
