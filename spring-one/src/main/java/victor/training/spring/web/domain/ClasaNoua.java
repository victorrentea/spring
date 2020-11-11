package victor.training.spring.web.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString.Exclude;

import javax.persistence.Entity;

//@Data NU pe entity pentru ca practic iti bagi piciorul in OOP OFICIAL.
@Entity
public class ClasaNoua {
   private String s;

//   @Exclude
//   @EqualsAndHashCode.Exclude
//   private List<Copil> copii; // lazy loaded by default

}



class CodMalefic {
   {
//      ClasaNoua clasaNoua = new ClasaNoua();
//      clasaNoua.setCopii(null);
//      clasaNoua.getCopii().c
   }
}