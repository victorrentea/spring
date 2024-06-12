package victor.training.spring.first;

public class SaNeJucamCuLombok {
  public static void main(String[] args) {
  }
}

//@Value
//class Z {
//  int z;
//}

// dar pe sub, Lombok genereaza:
final
class Z {
  private final int z;

  public Z(int z) {
    this.z = z;
  }

  public int getZ() {
    return this.z;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof Z)) return false;
    final Z other = (Z) o;
    if (this.getZ() != other.getZ()) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getZ();
    return result;
  }

  public String toString() {
    return "Z(z=" + this.getZ() + ")";
  }
}