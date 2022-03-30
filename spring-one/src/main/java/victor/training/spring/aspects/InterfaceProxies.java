package victor.training.spring.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class InterfaceProxies {
   public static void main(String[] args) {
      MathsImpl impl = new MathsImpl();

      InvocationHandler h = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName() + " with args " + Arrays.toString(args));
//            db.query("some prep SELECT before every single other SQL call"); // == x 2 no of DB calls.
            return method.invoke(impl, args);
         }
      };
      // JDK proxies generate objs implementing arbitrary interfaces at RUNTIME
      Maths obj = (Maths) Proxy.newProxyInstance(InterfaceProxies.class.getClassLoader(),
          new Class<?>[]{Maths.class}, h);

      new SomeClassNeedingMaths(obj).logic();
   }
}
class SomeClassNeedingMaths {
   private final Maths maths;
   SomeClassNeedingMaths(Maths mathsProxy) {
      this.maths = mathsProxy;
   }

   public void logic() {
      System.out.println(maths.sum(2, 1));
      System.out.println(maths.product(3, 2));
   }
}

interface Maths {
   int sum(int a, int b);
   int product(int a, int b);
}

class MathsImpl implements Maths {
   @Override
   public int sum(int a, int b) {
      return a+b;
   }
   @Override
   public int product(int a, int b) {
      return a*b;
   }
}

