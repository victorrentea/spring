package victor.training.spring.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ManualProxies {
   public static void main(String[] args) {
      MathematicsImpl real = new MathematicsImpl();
      InvocationHandler h = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Intercept method call " + method.getName() + " with args " + Arrays.toString(args)) ;
            return method.invoke(real, args);
         }
      };
      Mathematics math = (Mathematics) Proxy.newProxyInstance(ManualProxies.class.getClassLoader(),
          new Class<?>[]{Mathematics.class}, h);

      demoTime(math);

   }

   private static void demoTime(Mathematics math) {
      System.out.println("Who am I talking to ?  "+ math.getClass());
      System.out.println(math.sum(1,1));
      System.out.println(math.sum(2,0));
      System.out.println(math.sum(3,-1));
      System.out.println(math.sum(3,1));
      System.out.println(math.product(2,1));
   }
}

class MathematicsImpl implements Mathematics {

   @Override
   public int sum(int a, int b) {
      return a+b;
   }

   @Override
   public int product(int a, int b) {
      return a*b;
   }
}

interface Mathematics {
   int sum(int a, int b);
   int product(int a, int b);
}