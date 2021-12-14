package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class HaiSaNeJucam {

   public static void main(String[] args) {
      Matematica reala = new Matematica();


      // JDK proxies - doar pentru interfete
//      InvocationHandler handler = new InvocationHandler() {
//         @Override
//         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println("SRI: ai invocat metoda " + method.getName() + " cu param " + Arrays.toString(args));
//            return method.invoke(reala, args);
//         }
//      };
//      Matematica matematica = (Matematica) java.lang.reflect.Proxy.newProxyInstance(
//          HaiSaNeJucam.class.getClassLoader(),
//          new Class<?>[]{Matematica.class},
//          handler
//      );

      Callback callback = new MethodInterceptor() {
         @Override
         public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("SRI: ai invocat metoda " + method.getName() + " cu param " + Arrays.toString(args));
            return method.invoke(reala, args);
         }
      };
      Matematica matematica = (Matematica) Enhancer.create(Matematica.class, callback);

      System.out.println("Instanta reala : " + reala);
      System.out.println("Instanta proxy : " + matematica);
      System.out.println("false : " + (reala == matematica));

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

class Matematica {
   public int suma(int a, int b) {
      return a + b;
   }

   public int produs(int a, int b) {
      return a*b;
   }
}