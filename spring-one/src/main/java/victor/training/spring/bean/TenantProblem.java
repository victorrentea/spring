package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Configuration
public class TenantProblem  implements CommandLineRunner {
    @Autowired
    private ServiceResolver resolver;
    @Override
    public void run(String... args) throws Exception {
        resolver.resolve("RO").m();
        resolver.resolve("HU").m();
    }
}
@Component
class ServiceResolver {
    private final Map<String, IService> allImplems;
    private final DefaultService defaultService;
    private ServiceResolver(Map<String, IService> allImplems, DefaultService defaultService) {
        this.allImplems = allImplems;
        this.defaultService = defaultService;
    }
    public IService resolve(String countryIso) {
        return allImplems.getOrDefault(countryIso, defaultService);
    }
}

interface IService {
//    boolean accepts(String... params);
    void m();
}

@Service
class DefaultService implements IService {
    @Override
    public void m() {
        System.out.println("Default implem");
    }
}

@Service("RO")
class ROService implements IService {
    @Override
    public void m() {
        System.out.println("RO implem");
    }
}