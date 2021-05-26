package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ProxyManual {

   public static void main(String[] args) {
      ExpensiveOps real = new ExpensiveOps();

      // REQUIREMENT: Orice metoda publica e chemata din bizLogic, sa logezi parametrii de apel
//      ops = new ExpensiveOps() {
//         @Override
//         public Boolean isPrime(int n) {
//            log.debug("SRI: isPrime(" +n+")");
//            return super.isPrime(n);
//         }
//      };

      // FIX ASTA FACE SI SPRINGUL CAND PROXIAZA
      MethodInterceptor callback = new MethodInterceptor() {
         @Override
         public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            log.debug("SRI: invoking method {} with arguments {}",
                method.getName(),
                Arrays.toString(objects)
                );
            return method.invoke(real, objects);
         }
      };
      ExpensiveOps ops = (ExpensiveOps) Enhancer.create(ExpensiveOps.class, callback);

      bizLogic(ops);
   }

   private static void bizLogic(ExpensiveOps ops) {
      System.out.println(ops.isPrime$(2));
      System.out.println(ops.isPrime$(3));
      System.out.println(ops.isPrime$(4));
      System.out.println(ops.hashAllFiles(new File(".")));
   }
}

