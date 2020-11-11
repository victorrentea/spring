package victor.training.spring.web.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString.Exclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Entity
public class EntitateCuPretentii {
   enum Status {DRAFT, ACTIVE, DELETED}
   @Id
   @GeneratedValue
   @Getter
   private Long id;

   private String numeRequired;

   private Status status= Status.DRAFT;
   private LocalDateTime activationTime;

   @OneToMany(mappedBy = "parinte")
   private List<CopilCuminte> copii = new ArrayList<>();

   protected EntitateCuPretentii() { //pt sulfetul lui Hibernate
   }

   public EntitateCuPretentii(String numeRequired) {
      this.numeRequired = requireNonNull(numeRequired);
   }

   public String getNumeRequired() {
      return numeRequired;
   }

   public void setNumeRequired(String numeRequired) {
      // campuri a caror stare trebuie validata cumva
      this.numeRequired = requireNonNull(numeRequired);
   }

   // statemechine: DRAFT -> ACTIVE -> DELETED
//   public void setStatus(Status status) {
//      this.status = status;
//   }

   public void activate() {
      status = Status.ACTIVE;
      activationTime = LocalDateTime.now();
   }


   public List<CopilCuminte> getCopii() {
      return Collections.unmodifiableList(copii);
   }

   // ai grija de rel bidirectionale
   public void addCopil(CopilCuminte copilCuminte) {
      copii.add(copilCuminte);
      copilCuminte.setParinte(this);
   }

   public boolean isActive() {
      return status == Status.ACTIVE;
   }

   public boolean canTravelToBulgaria() {
      return true;//business rules.
   }

//   {
//      EntitateCuPretentii e = new EntitateCuPretentii();
//      e, getCopii().add(new CopilCuminte());
//
//   }
}
