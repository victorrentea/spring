package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Slf4j
public class InterfaceProxy {

   public static void main(String[] args) {
      //ma prefac ca sunt Spring
      MateImpl realImpl = new MateImpl();

      InvocationHandler h = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("invoc metoda " + method.getName() + " with args " + Arrays.toString(args));
            return method.invoke(realImpl, args);
         }
      };
      Mate mateProxy = (Mate) Proxy.newProxyInstance(InterfaceProxy.class.getClassLoader(),
          new Class<?>[]{Mate.class}, h);

      Clasa2 clasa2 = new Clasa2(mateProxy);
      clasa2.oraDeMate();
   }
}

class Clasa2 {
   private final Mate mate;

   public Clasa2(Mate mate) {
      this.mate = mate;
   }

   public void oraDeMate() {
      System.out.println(mate.suma(7, 113));

      System.out.println(mate.produs(20, 6));
      System.out.println(mate.produs(30, 4));
      System.out.println(mate.produs(30, 40));
   }
}
//class MateImpl implements Mate {}
interface Mate {
   int suma(int a, int b);
   int produs(int a, int b);
}
class MateImpl implements Mate{
   @Override
   public int suma(int a, int b) {
      return a+b;
   }

   @Override
   public int produs(int a, int b) {
      return a*b;
   }
}

// Key Points
// [1] Interface Proxy using JDK (java.lang.reflect.Proxy)
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
