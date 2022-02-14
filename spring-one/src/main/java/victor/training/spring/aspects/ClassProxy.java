package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassProxy {
// interface proxies
   public static void main(String[] args) {
      MathsImpl realImpl = new MathsImpl();
      Callback callback = new MethodInterceptor() {
         @Override
         public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("calling " + method.getName() + " with args " + Arrays.toString(args));
            return method.invoke(realImpl, args);
         }
      };
      // CGLIB (the core of Spring Aspects) generates a subclass of your beans in memory and injects the subclass in your @autowired fieds/ctors
      MathsImpl maths = (MathsImpl) Enhancer.create(MathsImpl.class, callback);

      logic(maths);
   }

   private static void logic(MathsImpl maths) {
      System.out.println(maths.sum(1, 1));
      System.out.println(maths.sum(2, 0));
      System.out.println(maths.sum(3, -1));
      System.out.println(maths.sum(3, 1));
      System.out.println(maths.product(2, 1));
   }

   @Component
   static class MathsImpl {
      public int sum(int a, int b) {
         return a + b;
      }
      public int product(int a, int b) {
         return a * b;
      }
   }
}


