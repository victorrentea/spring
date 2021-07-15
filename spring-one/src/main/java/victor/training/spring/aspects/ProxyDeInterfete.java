package victor.training.spring.aspects;


import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyDeInterfete { // acum de clase, fara nici o interfata
   public static void main(String[] args) {

      Matematica impl = new Matematica();

//      InvocationHandler h = new InvocationHandler() {
//         @Override
//         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println("Chemi metoda " + method.getName() + " cu param " + Arrays.toString(args));
//            return method.invoke(impl, args);
//         }
//      };

//      Matematica mateProxy = (Matematica) Proxy.newProxyInstance(
//          ProxyDeInterfete.class.getClassLoader(),
//          new Class<?>[]{ Matematica.class },
//          h);

      Callback callback = new MethodInterceptor() {
         @Override
         public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("Chemi metoda " + method.getName() + " cu param " + Arrays.toString(args));
            return method.invoke(impl, args);
         }
      };

      Matematica mateProxy = (Matematica) Enhancer.create(Matematica.class, callback);

      biz(mateProxy);
   }

   private static void biz(Matematica mate) {
      System.out.println("Cum Doamne iarta-ma ruleaza ceva intre mine si mate.suma : " +
                         "instanta de mate nu e ceea ce pare : " +mate.getClass());
      System.out.println(mate.suma(1,1));
      System.out.println(mate.suma(2,0));
      System.out.println(mate.suma(3,-1));

      System.out.println(mate.suma(3,2));
      System.out.println(mate.produs(2,1));
   }
}
class MatematicaHackedGenerataOnTheFlyDeCGLIB extends Matematica {
   @Override
   public int suma(int a, int b) {
      return super.suma(a, b);
   }
   @Override
   public int produs(int a, int b) {
      return super.produs(a, b);
   }
}

//interface Matematica {
//   int suma(int a, int b);
//   int produs(int a, int b);
//}

class Matematica/*Impl implements Matematica */{
   public int suma(int a, int b) {
      System.out.println("Nu pot sa cred!");
      return a + b;
   }
   public int produs(int a, int b) {
      return a * b;
   }
}