package victor.training.spring.web.entity;

import java.util.Objects;

public class TrainingId { // ID type pattern
   private final long id;

   @SuppressWarnings("unused")  // Spring uses this to map from a url part into a TrainingId
   public TrainingId(String id) {
      this(Long.parseLong(id));
   }
   public TrainingId(long id) {
      this.id = id;
   }

   public long id() {
      return id;
   }

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
