package victor.training.crypto;

import java.util.Random;

public class RandomNuERandom {
  public static void main(String[] args) {
    Random random1 = new Random(1);
    Random random2 = new Random(1);

    System.out.println(random1.nextInt());
    System.out.println(random2.nextInt());

    System.out.println(random1.nextInt());
    System.out.println(random2.nextInt());
  }
}
