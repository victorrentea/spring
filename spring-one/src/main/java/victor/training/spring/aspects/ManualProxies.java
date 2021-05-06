package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ManualProxies {
   public static void main(String[] args) {
      Mathematics real = new Mathematics();

      Callback callback = new MethodInterceptor() {
         @Override
         public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("Intercept method call " + method.getName() + " with args " + Arrays.toString(args)) ;
            return method.invoke(real, args);
         }
      };
      Mathematics math = (Mathematics) Enhancer.create(Mathematics.class, callback);

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

class Mathematics {

   public int sum(int a, int b) {
      return a+b;
   }

   public int product(int a, int b) {
      return a*b;
   }
}
