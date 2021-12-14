package victor.training.spring.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class HaiSaNeJucam {

   public static void main(String[] args) {
      MatematicaImpl reala = new MatematicaImpl();


      InvocationHandler handler = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("SRI: ai invocat metoda " + method.getName() + " cu param " + Arrays.toString(args));
            return method.invoke(reala, args);
         }
      };

      Matematica matematica = (Matematica) java.lang.reflect.Proxy.newProxyInstance(
          HaiSaNeJucam.class.getClassLoader(),
          new Class<?>[]{Matematica.class},
          handler
      );

      undevaInCodulTau(matematica);
   }

   private static void undevaInCodulTau(Matematica matematica) {
      System.out.println(matematica.suma(1, 1));
      System.out.println(matematica.suma(0, 2));
      System.out.println(matematica.suma(3, -1));
      System.out.println(matematica.produs(2, 1));
      System.out.println(matematica.produs(-2, -1));


      System.out.println(matematica.suma(3, 1));
   }

}
// interface MyRepo extends JpaRepository<..> {}
interface Matematica {
   int suma(int a, int b);
   int produs(int a, int b);
}

class MatematicaImpl implements Matematica {

   @Override
   public int suma(int a, int b) {
      return a + b;
   }

   @Override
   public int produs(int a, int b) {
      return a*b;
   }
}