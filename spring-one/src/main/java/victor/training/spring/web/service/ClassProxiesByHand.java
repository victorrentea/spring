package victor.training.spring.web.service;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class ClassProxiesByHand {
   public static void main(String[] args) {
      UserService realBeanInstance = new UserService();

      Callback h = new InvocationHandler() {
         @Override
         public Object invoke(Object o, Method method, Object[] args) throws Throwable {
//            if (i have in cache(args)) return from cache;
            Object r = method.invoke(realBeanInstance, args);
//            put in cache (args, r)
            return r;
         }
      };
      UserService classProxy = (UserService) Enhancer.create(UserService.class, h);
      System.out.println(classProxy.countUsers());
   }
}
