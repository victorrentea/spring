package victor.training.spring.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyDeInterfete {
   public static void main(String[] args) {

      MatematicaImpl impl = new MatematicaImpl();

      InvocationHandler h = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Chemi metoda " + method.getName() + " cu param " + Arrays.toString(args));
            return method.invoke(impl, args);
         }
      };

      Matematica mateProxy = (Matematica) Proxy.newProxyInstance(
          ProxyDeInterfete.class.getClassLoader(),
          new Class<?>[]{ Matematica.class },
          h);

      biz(mateProxy);
   }

   private static void biz(Matematica mate) {
      System.out.println(mate.suma(1,1));
      System.out.println(mate.suma(2,0));
      System.out.println(mate.suma(3,-1));

      System.out.println(mate.suma(3,2));
      System.out.println(mate.produs(2,1));
   }
}


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
      return a * b;
   }
}