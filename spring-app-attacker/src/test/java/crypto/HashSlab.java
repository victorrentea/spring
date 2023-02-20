package crypto;

import java.util.HashSet;
import java.util.Objects;

public class HashSlab {
  public static void main(String[] args) {
//    System.out.println("Emma".hashCode());
//    System.out.println("Emma Simona".hashCode());
    HashSet<DispersieVarza> set = new HashSet<>();

    set.add(new DispersieVarza()); // O(n) nu O(1)


  }
}

class DispersieVarza {
  String x,y;

  @Override
  public int hashCode() {
    return 1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DispersieVarza that = (DispersieVarza) o;
    return Objects.equals(x, that.x) && Objects.equals(y, that.y);
  }
}