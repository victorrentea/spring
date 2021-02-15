package victor.training.spring.life;

import java.util.HashMap;
import java.util.Map;

public class DeCeAmNevoieDeSpring {
}

class ServiceLocator {
   public static <T>  T getBeanInstance(Class<T> clazz) {
      try {
         if (mocks.containsKey(clazz)) {
            return (T) mocks.get(clazz);
         }
         return clazz.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }
   private static Map<Class<?>, Object> mocks = new HashMap<>();

   @Deprecated
   public void ___setMockFromTests_____(Class<?> deMokcuit, Object mockitoObject) {
      mocks.put(deMokcuit, mockitoObject);
   }
   public static void clearMocks() {
      mocks.clear();
   }
}

// la ce e bun Spring;
// 1 lifecycle mgmt : sa definesti ca singleton si sa se ocupe el
// 2 Dep Injection
//----------------- pana aici : foloseste Google Guice (de 100x mai lightweight)
// 3 Proxy-uri : @Transactional, @Cacheable, @Async, @PreAuthorize, "thread" scope
// 4 O tona de cod helper deja scris: JdbcTemplate, RestTemplate, JmsTemplate
// 5. Front Controller (~Struts) API rest
// 6. integrari cu tehnologii: cozi, quartzuri, MBeans
// 7. Fitze: microservicii,

// 8. Spring Batch: retry. pause-resume, tracking de status, %done, parametri persistenti. chunk-based processing: la 500 COMMIT, ... ERROR, Merge si cu JPA
// Spring Cloud Flow pt orchestrari de joburi intre JVM-uri.




class Firaraie {
   public void initToate() {

   }
}

class A {

   // dureaza 2 sec ca incaraca  fisiere, creaza tabele.
   private B b = ServiceLocator.getBeanInstance(B.class); // pull-based mechanism


   public void method() {
      b.method();// cum testezi
   }
   public void method2() {
      b.method();// cum testezi
   }
}

class B {
   public void method() {
       // de chemat
   }
}