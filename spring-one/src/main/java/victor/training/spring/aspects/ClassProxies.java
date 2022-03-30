package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassProxies {
   public static void main(String[] args) {
      Maths impl = new Maths();


      Callback handler = new InvocationHandler() {
         @Override
         public Object invoke(Object o, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName() + " with args " + Arrays.toString(args));
            return method.invoke(impl, args);
         }
      };
      Maths proxy = (Maths) Enhancer.create(Maths.class, handler); // generates a dynamic subclass of your type
      // EXACTLY like this
//      Maths proxy = new Maths() {
//         @Override
//         public int sum(int a, int b) {
//            System.out.println("sum with args  " + a + "," + b);
//            return impl.sum(a, b);
//         }
//      };

      new SomeClassNeedingMaths(proxy).logic();
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

/*final THROWS */class Maths {
   public/* final silently ignored*/ int sum(int a, int b) {
      return a+b;
   }
   protected int product(int a, int b) {
      return a*b;
   }
}