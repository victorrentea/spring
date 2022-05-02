package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ClassProxy {

   public static void main(String[] args) {
      //ma prefac ca sunt Spring
      Mate realImpl = new Mate();

//      InvocationHandler h = new InvocationHandler() {
//         @Override
//         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            log.info("invoc metoda " + method.getName() + " with args " + Arrays.toString(args));
//            return method.invoke(realImpl, args);
//         }
//      };
//      Mate mateProxy = (Mate) Proxy.newProxyInstance(InterfaceProxy.class.getClassLoader(),
//          new Class<?>[]{Mate.class}, h);

      Callback h = new MethodInterceptor() {
         @Override
         public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            log.info("SRI,FSB,KGB,MI6,CIA,Soacra: invoc metoda " + method.getName() + " with args " + Arrays.toString(args));
            return method.invoke(realImpl, args);
         }
      };
      // de fapt CGLIB face asta:
//      class Noua  extends Mate{
//         @Override
//         public int produs(int a, int b) {
//            //chestiile tale
////            return realImpl.produs();
//         }
//      }
      Mate mateProxy =
          //new Noua();
          (Mate) Enhancer.create(Mate.class, h);

      Clasa2 clasa2 = new Clasa2(mateProxy);
      clasa2.oraDeMate();
   }
}

//@Service
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
//@Service
/*final*/ class Mate{
   public /*final*/ int suma(int a, int b) {
      return a+b;
   }
   public int produs(int a, int b) {
      int produs = 0;
      for (int i = 0; i < a; i++) {
         produs = suma(produs, b);
      }
      return produs;
//      return a*b;
   }
}
// 3 capcane in lucrul cu proxy-uri in spring:
// 1 final pe clasa > eroare
// 2 final pe metoda > silent ignore (mai dramatic)
// 3 APELURILE LOCALE INTRE METODE DIN ACEEASI INSTANTA, NU SUNT PROXY-ATE (INTERCEPTATE)


// Key Points
// [1] Interface Proxy using JDK (java.lang.reflect.Proxy)
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor

