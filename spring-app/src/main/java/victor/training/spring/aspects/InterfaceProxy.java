package victor.training.spring.aspects;

public class InterfaceProxy {

}


// Key Points
// [1] Interface Proxy using JDK (java.lang.reflect.Proxy)
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
