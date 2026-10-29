package victor.training.spring.first.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import victor.training.spring.first.Z;

@Configuration//(proxyBeanMethods = false)
public class ZConfig {
    @Bean
    public Z z() {
        System.out.println("De cate ori iubito?");
        return new Z();
    }
    @Bean
    public W w() {
        System.out.println("W in " + getClass());
        return new W(z());
    }
    @Bean
    public V v() {
        System.out.println("V");
        return new V(z());
    }
}
//class Tzeapa extends ZConfig {
//    @Override
//    public Z z() {
//        return super.z();
//    }
//}
record W(Z z) {}
record V(Z z) {}
