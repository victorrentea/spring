package victor.training.spring.web.controller;

import java.security.SecureRandom;
import java.util.Random;

public class RandomNuERandom {
   public static void main(String[] args) {

      Random random = new Random(10);
      System.out.println(random.nextInt(10000000));
      System.out.println(random.nextInt(10000000));
      System.out.println(random.nextInt(10000000));
      System.out.println(random.nextInt(10000000));
      System.out.println(random.nextInt(10000000));
      System.out.println(random.nextInt(10000000));
      System.out.println(random.nextInt(10000000));
      System.out.println(random.nextInt(10000000));

//      SecureRandom random = new SecureRandom();
//
//
//
//
//      while (true) {
//         double newR = Math.random();
//         if (newR == lastR) {
//            throw new IllegalArgumentException("Imposibil!");
//         }
//         lastR = newR;
//      }

   }
}
