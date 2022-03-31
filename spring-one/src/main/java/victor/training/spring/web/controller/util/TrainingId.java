package victor.training.spring.web.controller.util;

import java.util.Objects;

// ID types. "microtype"
public class TrainingId {
   private final long id;

   TrainingId(String id) {
      this(Long.parseLong(id));
   }
   TrainingId(long id) {
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
