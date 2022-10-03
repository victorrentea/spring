package victor.training.spring.web.entity;

import java.util.Objects;

public class TrainingId { // ID type pattern
   private final long id;

   public TrainingId(String id) {
      this(Long.parseLong(id));
   }
   public TrainingId(long id) {
      this.id = id;
   }

   public long id() {
      return id;
   }
// WARNING: nu ma sterge ca sprigu nu mai merge alfel
//   public static TrainingId valueOf(String value) {
//      return new TrainingId(value);
//   }
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TrainingId that = (TrainingId) o;
      return id == that.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }
}
