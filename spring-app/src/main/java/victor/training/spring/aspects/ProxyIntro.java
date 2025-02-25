package victor.training.spring.aspects;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

public class ProxyIntro {
  private static final Logger log = LoggerFactory.getLogger(ProxyIntro.class);

  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths maths = new Maths();
//    Maths decorator = new LoggingDecorator(maths);
    Callback callHandler = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("{} ({})", method.getName(), Arrays.toString(args));
        return method.invoke(maths, args);
      }
    };
    Maths decorator = (Maths) Enhancer.create(Maths.class, callHandler);
    SecondGrade secondGrade = new SecondGrade(decorator);
    secondGrade.mathClass();
  }
}
// this is how class-proxies (the normal ones) work in Spring
// 💡 class with the same "interface" that will log the args before delegating the call to the real methods in Maths
//class LoggingDecorator extends Maths {
//  private static final Logger log = LoggerFactory.getLogger(LoggingDecorator.class);
//  private final Maths maths;
//  public LoggingDecorator(Maths maths) {
//    this.maths = maths;
//  }
//  public int sum(int a, int b) {
//    log.info("sum({},{})", a, b);
//    return maths.sum(a, b);
//  }
//  public int product(int a, int b) {
//    log.info("product({},{})", a, b);
//    return maths.product(a, b);
//  }
//}
// Log the arguments of Maths#sum() and Maths#product() methods without changing any code below this line
// ------------------- LINE ------------------
// change something below the line to STOP proxies from working
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
//    Maths maths = new Maths(); // WHY no DI?🥺
    // eg Dto/Mongo Document/jooq Record is never INJECTED by Spring => @Timed.... dont work
    System.out.println("What instance of Maths am I really injected ? " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
/*final=crash*/
/*record=crash*/
class Maths {
//  @Timed
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  @Secured("ROLE_ADMIN")
//  @Transactional
//  @Cacheable
  // @Secured("ROLE_CAN_DO_SUM")
  public /*final=ignored|crash*/ int sum(int a, int b) {
    return a + b;
  }
  // @Secured("ROLE_CAN_DO_PRODUCT") // you might (WRONG) expect that the user will also be demanded to have
  // ROLE_..SUM
  // @Secured({"ROLE_CAN_DO_PRODUCT", "ROLE_CAN_DO_SUM"}) << correct way
  public /*static eg util=ignored*/ int product(int a, int b) {
    int result = 0;
    for (int i = 0; i < a; i++) {
      result = sum(result, b); // 👑 a local call is NEVER instercepted
    }
    return result;
  }
}
