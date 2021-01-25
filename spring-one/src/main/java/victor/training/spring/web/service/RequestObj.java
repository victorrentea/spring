package victor.training.spring.web.service;

import java.util.Objects;

public class RequestObj {
   private final long id;

   public RequestObj(long id) {
      this.id = id;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      RequestObj that = (RequestObj) o;
      return id == that.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }

   public long getId() {
      return id;
   }
}
