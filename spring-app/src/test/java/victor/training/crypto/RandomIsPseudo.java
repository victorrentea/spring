package victor.training.crypto;

import java.util.Random;

public class RandomIsPseudo {
  public static void main(String[] args) {

    Random random = new Random(1);
//    Random random = new Random();
    System.out.println(random.nextInt(100));
    System.out.println(random.nextInt(100));
    System.out.println(random.nextInt(100));
    System.out.println(random.nextInt(100));
  }
}
