package crypto;

import java.util.Random;

public class RandomIsNotRandom {
    public static void main(String[] args) {
        for (int i = 0; i < 10_00000000; i++) {
        Random r1 = new Random();
        Random r2 = new Random();

            int v1 = r1.nextInt();
            int v2 = r2.nextInt();
            System.out.println(v1);
            System.out.println(v2);
            System.out.println(r1.nextInt());
            System.out.println(r2.nextInt());
            System.out.println(r1.nextInt());
            System.out.println(r2.nextInt());
            System.out.println(r1.nextInt());
            System.out.println(r2.nextInt());
            System.out.println(r1.nextInt());
            System.out.println(r2.nextInt());
            System.out.println(r1.nextInt());
            System.out.println(r2.nextInt());
            System.out.println(r1.nextInt());
            System.out.println(r2.nextInt());

            if (r1.nextInt() == r2.nextInt()) throw new IllegalArgumentException("WTH ?!!!");

        }
    }
}
