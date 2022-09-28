package victor.training.spring.web.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES) // force CGLIB to proxy the class, and not use java.lang.reflect.Proxy
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiceImpl implements ServiceInterface {
    @Override
//    @Cacheable
    public int stuff(int x) {
        new RuntimeException().printStackTrace();
        return x + 1;
    }
}
