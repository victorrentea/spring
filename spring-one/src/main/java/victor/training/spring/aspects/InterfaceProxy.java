package victor.training.spring.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class InterfaceProxy {
// interface proxies
   public static void main(String[] args) {
      MathsImpl realImpl = new MathsImpl();
      InvocationHandler h = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("calling " + method.getName() + " with args " + Arrays.toString(args));
            return method.invoke(realImpl, args);
         }
      };
      Maths maths = (Maths) Proxy.newProxyInstance(InterfaceProxy.class.getClassLoader(),
          new Class<?>[]{Maths.class}, h);

      System.out.println(maths.sum(1, 1));
      System.out.println(maths.sum(2, 0));
      System.out.println(maths.sum(3, -1));
      System.out.println(maths.sum(3, 1));
      System.out.println(maths.product(2, 1));
   }
   interface Maths {
      int sum(int a, int b);
      int product(int a, int b);
   }
   static class MathsImpl implements Maths {
      @Override
      public int sum(int a, int b) {
         return a + b;
      }

      @Override
      public int product(int a, int b) {
         return a * b;
      }
   }
}


